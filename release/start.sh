#!/usr/bin/env bash
java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.rmi.port=9090 -Dcom.sun.management.jmxremote=true \
 -Dcom.sun.management.jmxremote.port=9090 -Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.local.only=false \
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 --add-opens java.base/java.nio=io.netty.common \
 --add-exports java.base/jdk.internal.misc=io.netty.common -p modules -m gomint.server/io.gomint.server.Bootstrap
