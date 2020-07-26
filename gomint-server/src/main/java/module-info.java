module gomint.server {
    // Java modules
    requires java.desktop;
    requires jdk.unsupported;
    requires java.management;
    requires java.compiler;
    requires compiler;

    // Gomint modules
    requires gomint.taglib;
    requires gomint.api;
    requires gomint.jraknet;
    requires gomint.leveldb;
    requires gomint.crypto;

    // Logging modules
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires io.sentry;

    // Netty modules
    requires io.netty.codec;
    requires io.netty.transport;
    requires io.netty.buffer;
    requires io.netty.common;

    // Spring modules
    requires spring.beans;
    requires spring.context;

    // Compile modules
    requires com.fasterxml.jackson.core;
    requires lombok;

    // Automatic modules (libs without module-info)
    requires it.unimi.dsi.fastutil;
    requires jopt.simple;
    requires com.google.common;
    requires org.apache.commons.io;
    requires json.simple;
    requires oshi.core;
    requires jsr305;
    requires jline.reader;
    requires jline.terminal;
    requires spring.core;
    requires org.apache.commons.text;
    requires org.slf4j;

    // Export logging stuff for log4j2
    exports net.minecrell.terminalconsole to org.apache.logging.log4j, org.apache.logging.log4j.core;
    exports io.gomint.server.logging to org.apache.logging.log4j, org.apache.logging.log4j.core;

    // Export stuff to spring
    exports io.gomint.server to spring.beans;
    exports io.gomint.server.util to spring.beans;
    exports io.gomint.server.inventory.item to spring.beans;
    exports io.gomint.server.network to spring.beans;
    exports io.gomint.server.network.handler to spring.beans;
    exports io.gomint.server.plugin to spring.beans;
    exports io.gomint.server.world.generator.vanilla to spring.beans;

    opens io.gomint.server.network to spring.core;
    opens io.gomint.server.network.handler to spring.core;
    opens io.gomint.server.entity to spring.core;
    opens io.gomint.server.entity.tileentity to spring.core;

    // Open config to gomint api reader
    opens io.gomint.server.config to gomint.api;
}
