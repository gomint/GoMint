module gomint.jni {
    requires oshi.core;
    requires com.google.common;
    requires io.netty.buffer;
    requires slf4j.api;

    exports io.gomint.server.jni;
    exports io.gomint.server.jni.exception;
    exports io.gomint.server.jni.hash;
    exports io.gomint.server.jni.zlib;
}
