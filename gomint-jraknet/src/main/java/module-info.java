module gomint.jraknet {
    requires slf4j.api;

    requires io.netty.transport;
    requires io.netty.buffer;
    requires io.netty.transport.epoll;
    requires io.netty.common;

    exports io.gomint.jraknet;
}