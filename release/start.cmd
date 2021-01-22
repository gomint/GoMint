@echo off
java -Dio.netty.buffer.checkBounds=false --add-modules ALL-DEFAULT --add-opens java.base/java.nio=io.netty.common --add-exports java.base/jdk.internal.misc=io.netty.common -p modules -m gomint.server/io.gomint.server.Bootstrap
