#!/usr/bin/env bash
java --add-opens java.base/java.nio=io.netty.common --add-exports java.base/jdk.internal.misc=io.netty.common -p modules -m gomint.server/io.gomint.server.Bootstrap
