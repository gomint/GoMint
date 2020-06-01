package io.gomint.jraknet;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class RakNetConstraints {

    // =========================== CONSTANTS =========================== //

    // Version of the RakNet library's protocol MCPE is using
    public static final byte RAKNET_PROTOCOL_VERSION_MOJANG = 9;

    // Version of the original RakNet lib without modifications
    public static final byte RAKNET_PROTOCOL_VERSION = 6;

    // Number of ordering channels RakNet supports at max
    public static final int NUM_ORDERING_CHANNELS = 32;

    // Internal constant used by RakNet (may be seen here: https://github.com/OculusVR/RakNet/blob/master/Source/MTUSize.h)
    public static final int MINIMUM_MTU_SIZE = 576;

    // Internal constant used by RakNet (may be seen here: https://github.com/OculusVR/RakNet/blob/master/Source/MTUSize.h)
    public static final int MAXIMUM_MTU_SIZE = 1464; // Vanilla only goes up to this

    // Byte Signature used to identify unconnected data packets for yet unconnected connections (may be seen here: https://github.com/OculusVR/RakNet/blob/master/Source/RakPeer.cpp#L135)
    public static final byte[] OFFLINE_MESSAGE_DATA_ID = new byte[]{ (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78 };

    // Custom timeout value (not found in RakNet as far as I know)
    public static final long CONNECTION_TIMEOUT_MILLIS = 10000L;

    // The maximum length of any encapsulated datagram's header
    public static final int DATA_HEADER_BYTE_LENGTH = 28;

    public static final int MAX_MESSAGE_HEADER_BYTE_LENGTH = 23;

    // The maximum number of local IPs sent to a remote system
    public static final int MAX_LOCAL_IPS = 10;


    // =========================== PACKET IDS =========================== //

    // May be seen here: https://github.com/OculusVR/RakNet/blob/master/Source/MessageIdentifiers.h

    // Any -> Any
    public static final byte CONNECTED_PING = (byte) 0x00;

    // Client -> Server
    public static final byte UNCONNECTED_PING = (byte) 0x01;

    // Client -> Server
    public static final byte UNCONNECTED_PING_OPEN_CONNECTION = (byte) 0x02;

    // Any -> Any
    public static final byte CONNECTED_PONG = (byte) 0x03;

    // Server -> Client
    public static final byte DETECT_LOST_CONNECTION = (byte) 0x04;

    // Client -> Server
    //
    // Sent in order to establish a viable connection
    // Its size determines the maximum MTU size the connection can provide
    public static final byte OPEN_CONNECTION_REQUEST_1 = (byte) 0x05;

    // Server -> Client
    public static final byte OPEN_CONNECTION_REPLY_1 = (byte) 0x06;

    // Client -> Server
    public static final byte OPEN_CONNECTION_REQUEST_2 = (byte) 0x07;

    // Server -> Client
    public static final byte OPEN_CONNECTION_REPLY_2 = (byte) 0x08;

    // Client -> Server
    public static final byte CONNECTION_REQUEST = (byte) 0x09;

    // Server -> Client
    public static final byte CONNECTION_REQUEST_ACCEPTED = (byte) 0x10;

    // Server -> Client
    public static final byte CONNECTION_REQUEST_FAILED = (byte) 0x11;

    // Server -> Client
    public static final byte ALREADY_CONNECTED = (byte) 0x12;

    // Client -> Server
    public static final byte NEW_INCOMING_CONNECTION = (byte) 0x13;

    // Server -> Client
    public static final byte NO_FREE_INCOMING_CONNECTIONS = (byte) 0x14;

    // Any -> Any
    public static final byte DISCONNECTION_NOTIFICATION = (byte) 0x15;

    // Server -> Client
    public static final byte INCOMPATIBLE_PROTOCOL_VERSION = (byte) 0x19;

    // Server -> Client
    //
    // Note: Mojang made modifications to this packet's original data
    //       They appended a string containing the server's MOTD at the end
    public static final byte UNCONNECTED_PONG = (byte) 0x1C;


    public static final byte USER_PACKET_ENUM = (byte) 0x80;

    private RakNetConstraints() {
        throw new AssertionError( "Cannot instantiate RakNetConstraints" );
    }

}
