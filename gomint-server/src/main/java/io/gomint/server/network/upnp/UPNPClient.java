/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.upnp;

import io.gomint.server.async.Future;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UPNPClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(UPNPClient.class);
    private static final String DISCOVER_MESSAGE_ROOTDEVICE =
        "M-SEARCH * HTTP/1.1\r\n" +
            "MX: 5\r\n" +
            "HOST: 239.255.255.250:1900\r\n" +
            "MAN: \"ssdp:discover\"\r\n" +
            "ST: upnp:rootdevice\r\n\r\n";

    private final Future<String> pnpClientURL = new Future<>();
    private final InetSocketAddress groupAddress;
    private InetAddress localAddress;

    public UPNPClient() {
        this.groupAddress = new InetSocketAddress("239.255.255.250", 1900);
        this.run();
    }

    private static class MulticastHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        private final EventLoopGroup group;
        private final Future<String> future;

        public MulticastHandler(EventLoopGroup group, Future<String> future) {
            this.group = group;
            this.future = future;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket p) throws Exception {
            ByteBuf buf = p.content();
            byte[] b = new byte[buf.readableBytes()];
            buf.readBytes(b);
            final String data = new String(b, 0, b.length, StandardCharsets.UTF_8);

            for (String line : data.split("\n")) {
                if (line.startsWith("LOCATION:")) {
                    String upnpUrl = line.substring("LOCATION:".length()).trim();

                    URL url = new URL(upnpUrl);

                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = builderFactory.newDocumentBuilder();
                    Document xmlDocument = builder.parse(url.openStream());
                    XPath xPath = XPathFactory.newInstance().newXPath();

                    String out = xPath.compile("//device[deviceType=\"urn:schemas-upnp-org:device:InternetGatewayDevice:1\"]" +
                        "/deviceList/device[deviceType=\"urn:schemas-upnp-org:device:WANDevice:1\"]" +
                        "/deviceList/device[deviceType=\"urn:schemas-upnp-org:device:WANConnectionDevice:1\"]" +
                        "/serviceList/service[serviceType=\"urn:schemas-upnp-org:service:WANIPConnection:1\"]" +
                        "/controlURL").evaluate(xmlDocument);

                    if (!out.startsWith("/")) {
                        out = "/" + out;
                    }

                    this.future.resolve(String.format("http://%s:%d%s", url.getHost(), url.getPort(), out));
                    ctx.channel().close().addListener(future -> {
                        this.group.shutdownGracefully();
                    });
                }
            }
        }
    }

    private void run() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        try {
            Optional<NetworkInterface> optNi = NetworkInterface.networkInterfaces().filter(ni -> {
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                if (!addresses.hasMoreElements()) {
                    return false;
                }

                InetAddress address = addresses.nextElement();
                if (address.isLoopbackAddress()) {
                    return false;
                }

                return address instanceof Inet4Address;
            }).findAny();

            if (optNi.isPresent()) {
                NetworkInterface ni = optNi.get();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                InetAddress address = addresses.nextElement();

                this.localAddress = address;

                Bootstrap serverBootstrap = new Bootstrap()
                    .group(group)
                    .channelFactory((ChannelFactory<NioDatagramChannel>) () -> new NioDatagramChannel(InternetProtocolFamily.IPv4))
                    .localAddress(address, 52378)
                    .option(ChannelOption.IP_MULTICAST_IF, ni)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        public void initChannel(NioDatagramChannel ch) {
                            ch.pipeline().addLast(new MulticastHandler(group, UPNPClient.this.pnpClientURL));
                        }
                    });
                serverBootstrap.bind()
                    .addListener((ChannelFutureListener) channelFuture -> sendDiscoveryPacket((NioDatagramChannel) channelFuture.channel()));
            } else {
                this.pnpClientURL.fail(new Exception("no network interface"));
                LOGGER.warn("Could not detect network interface. Aborting...");
            }
        } catch (SocketException e) {
            this.pnpClientURL.fail(e);
            LOGGER.warn("Could not receive SSDP discovery", e);
        }
    }

    private void sendDiscoveryPacket(NioDatagramChannel channel) {
        byte[] txbuf = DISCOVER_MESSAGE_ROOTDEVICE.getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = Unpooled.copiedBuffer(txbuf);
        channel.writeAndFlush(new io.netty.channel.socket.DatagramPacket(buf, this.groupAddress, channel.localAddress()));
    }

    public void portForward(int port) {
        if (this.localAddress == null) {
            return;
        }
        
        String body =
            "<u:AddPortMapping xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\">" +
                "<NewRemoteHost></NewRemoteHost>" +
                "<NewExternalPort>" + port + "</NewExternalPort>" +
                "<NewProtocol>UDP</NewProtocol>" +
                "<NewInternalPort>" + port + "</NewInternalPort>" +
                "<NewInternalClient>" + this.localAddress.getHostAddress() + "</NewInternalClient>" +
                "<NewEnabled>1</NewEnabled>" +
                "<NewPortMappingDescription>GoMint</NewPortMappingDescription>" +
                "<NewLeaseDuration>0</NewLeaseDuration>" +
                "</u:AddPortMapping>";

        String contents =
            "<?xml version=\"1.0\"?>" +
                "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" +
                "<s:Body>" + body + "</s:Body></s:Envelope>";

        try {
            byte[] data = contents.getBytes();

            URL url = new URL(this.pnpClientURL.get(5, TimeUnit.SECONDS));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setRequestProperty("SOAPAction", "urn:schemas-upnp-org:service:WANIPConnection:1#AddPortMapping");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            connection.setDoOutput(true);
            connection.getOutputStream().write(data);
            if (connection.getResponseCode() == 200) {
                LOGGER.warn("Successfully opened port {}", port);
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            LOGGER.warn("Failed to call port mapping url", e);
        } catch (TimeoutException e) {
            LOGGER.warn("Did not find a uPNP root device");
        }
    }

}
