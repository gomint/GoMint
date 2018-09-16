#!/bin/sh
CXX="g++ -s -shared -static -fPIC -static-libgcc -static-libstdc++ -D_WIN32_WINNT=0x0600 -D_WIN32_WINDOWS=0x0600 -fPIC -O3 -Wall -Werror -I$JAVA_HOME/include/ -I$JAVA_HOME/include/win32/"

echo $CXX
$CXX src/main/c/zlib.cpp -o src/main/resources/zlib.dll -lz
$CXX src/main/c/hash.cpp -o src/main/resources/hash.dll -lcrypto -lgdi32
