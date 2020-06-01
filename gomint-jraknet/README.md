# jRakNet

This project is part of the GoMint.io project and aims to be a reliable port of the original RakNet
networking library written in C++ for Java. It is developed specifically to meet the requirements
of the GoMint server implementation but might be used in other projects, too, if slightly adapted.

## Usage

jRakNet supports both server-side and client-side connections through two different types of sockets.

Creating a client socket:
```Java
ClientSocket client = new ClientSocket();
client.setEventHandler( handler );
try {
    client.initialize()
} catch ( SocketException e ) {
    logger.error( "Failed to initialize client socket", e );
}
client.connect( "127.0.0.1", 19112 );
```

Creating a server socket:
```Java
ServerSocket server = new ServerSocket( 10 );
server.setEventHandler( handler );
try {
    server.bind( "0.0.0.0", 19112 );
} catch ( SocketException e ) {
    logger.error( "Failed to bind server socket", e );
}
```

## License

jRakNet is distributed under the terms of the BSD license which may be found inside the LICENSE file
inside the source tree's root directory.

## Documentation

For further details you may take a look at the Javadocs of the latest version of jRakNet here:
https://www.gomint.io/docs/jraknet/

You may always generate the Javadoc of the particular version you are using from the source code
itself.

## Maven Repository

In case you are using Maven inside your project, feel free to use the GoMint project's own maven
repository to grab jRakNet. Simply add the following lines to your pom.xml

```XML
<repositories>
    <repository>
        <id>gomint-repo</id>
        <name>GoMint Public Repository</name>
        <url>https://repo.gomint.io/content/groups/public/</url>
    </repository>
</repositories>
```

## Authors

jRakNet was mainly developed by BlackyPaw with the support of geNAZt for several tests.

## Contact

If you wish to get in contact feel free to write an email: blackypaw [at] gomint [dot] io
