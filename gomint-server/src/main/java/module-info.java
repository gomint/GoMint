module gomint.server {
    // Java modules
    requires java.desktop;
    requires jdk.unsupported;
    requires java.management;

    // Gomint modules
    requires gomint.taglib;
    requires gomint.api;
    requires gomint.jni;
    requires gomint.jraknet;
    requires gomint.leveldb;

    // Logging modules
    requires slf4j.api;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    // Netty modules
    requires io.netty.codec;
    requires io.netty.transport;
    requires io.netty.buffer;

    // Spring modules
    requires spring.beans;
    requires spring.context;

    // Compile modules
    requires lombok;

    // Automatic modules (libs without module-info)
    requires it.unimi.dsi.fastutil;
    requires jopt.simple;
    requires jline;
    requires com.google.common;
    requires javassist;
    requires org.apache.commons.io;
    requires json.simple;
    requires oshi.core;
    requires jsr305;

    // Export logging stuff for log4j2
    exports net.minecrell.terminalconsole to org.apache.logging.log4j, org.apache.logging.log4j.core;
    exports io.gomint.server.logging to org.apache.logging.log4j, org.apache.logging.log4j.core;
}
