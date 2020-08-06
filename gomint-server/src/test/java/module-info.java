open module gomint.test {
    requires gomint.api;
    requires gomint.server;
    requires gomint.jraknet;
    requires gomint.taglib;
    requires jopt.simple;
    requires net.bytebuddy;
    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
    requires org.mockito;
    requires spring.context;
    requires spring.beans;
}
