/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.emulator;

import io.gomint.emulator.client.Client;
import io.gomint.jraknet.ClientSocket;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BootStrap {

    public static void main(String[] args) throws InterruptedException {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the number of emulated players: ");
        int amountOfBots = reader.nextInt();

        System.out.println("Enter the IP of the server: ");
        String ip = reader.nextLine();

        System.out.println("Enter the port of the server: ");
        int port = reader.nextInt();

        reader.close();


        Configurator.setLevel("io.gomint.jraknet.ClientSocket", Level.ERROR);

        ScheduledExecutorService service = Executors.newScheduledThreadPool(amountOfBots + 1); // Amount of cores
        PostProcessExecutorService postProcessExecutorService = new PostProcessExecutorService();

        service.scheduleAtFixedRate(Client::printDebug, 5, 5, TimeUnit.SECONDS);

        for (int i = 0; i < 1; i++) {
            service.execute(() -> {
                Client client = new Client(service, postProcessExecutorService.getExecutor(), null);
                client.connect(ip, port);

                ClientSocket socket = new ClientSocket();
                socket.setMojangModificationEnabled(true);
                socket.setEventHandler((socket1, socketEvent) -> {
                    if (socketEvent.getType() == SocketEvent.Type.UNCONNECTED_PONG) {
                        SocketEvent.PingPongInfo info = socketEvent.getPingPongInfo();
                        System.out.println("Pinged: " + info.getMotd());
                    }
                });
                try {
                    socket.initialize();
                    socket.ping(ip, port);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            });

            Thread.sleep(1000);
        }
    }

}
