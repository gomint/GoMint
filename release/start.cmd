@echo off
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 --add-opens java.base/java.nio=io.netty.common --add-exports java.base/jdk.internal.misc=io.netty.common -p modules -m gomint.server/io.gomint.server.Bootstrap
