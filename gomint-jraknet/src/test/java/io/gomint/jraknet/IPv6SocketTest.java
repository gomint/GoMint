package io.gomint.jraknet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class IPv6SocketTest {
	
	//////////////////////////////////////////////////////////////////////////
	private static boolean initialized = false;
	
	private static ServerSocket serverSocket;
	private static ClientSocket clientSocket;
	
	private static Connection serverboundConnection;
	private static Connection clientboundConnection;
	
	private static boolean connectionAttemptComplete;
	
	//////////////////////////////////////////////////////////////////////////
	@BeforeClass
	public static void initialize() {
		if ( initialized ) {
			return;
		}
		serverSocket = new ServerSocket( 1 );
		initialized = true;
	}
	
	@AfterClass
	public static void cleanup() {
		if ( clientboundConnection != null ) {
			clientboundConnection.disconnect( "Disconnected" );
			clientboundConnection = null;
		}
		if ( serverboundConnection != null ) {
			serverboundConnection.disconnect( "Disconnected" );
			serverboundConnection = null;
		}
		
		if ( clientSocket != null ) {
			clientSocket = null;
		}
		if ( serverSocket != null ) {
			serverSocket.close();
			clientSocket = null;
		}
	}
	//////////////////////////////////////////////////////////////////////////
	
	// Cumbersome Letters added into test method names to execute them in a specific order
	
	@Test
	public void testAIPv6Bind() throws SocketException, UnknownHostException {
		byte[] loopbackAddress = new byte[16];
		loopbackAddress[15] = 1;
		InetAddress loopback = Inet6Address.getByAddress( "localhost", loopbackAddress, 0 );
		serverSocket.setMojangModificationEnabled( true );
		serverSocket.setEventHandler( new SocketEventHandler() {
			@Override
			public void onSocketEvent( Socket socket, SocketEvent event ) {
				handleSocketEvent( socket, event );
			}
		} );
		serverSocket.bind( new InetSocketAddress( loopback, 19132 ) );
	}
	
	@Test
	public void testBIpv6Connect() throws SocketException {
		connectionAttemptComplete = false;
		
		ClientSocket clientSocket = new ClientSocket();
		clientSocket.setMojangModificationEnabled( true );
		clientSocket.setEventHandler( new SocketEventHandler() {
			@Override
			public void onSocketEvent( Socket socket, SocketEvent event ) {
				handleSocketEvent( socket, event );
			}
		} );
		clientSocket.initialize();
		clientSocket.connect( serverSocket.getBindAddress() );
		
		while ( !connectionAttemptComplete ) {
			try {
				Thread.sleep( 10L );
			} catch ( InterruptedException ignored ) {
				// ._.
			}
		}
		
		long start = System.currentTimeMillis();
		long now;
		while ( clientboundConnection == null ) {
			now = System.currentTimeMillis();
			if ( now > start + 1000L ) {
				break;
			}
			
			try {
				Thread.sleep( 10L );
			} catch ( InterruptedException ignored ) {
				// ._.
			}
		}
		
		if ( serverboundConnection == null || clientboundConnection == null ) {
			throw new SocketException( "Could not connect to server socket" );
		}
	}
	
	@Test
	public void testCSendReceive() throws SocketException {
		byte[] packetData = "IPv6-Test".getBytes();
		
		clientboundConnection.send( PacketReliability.RELIABLE_ORDERED, 0, packetData );
		serverboundConnection.send( PacketReliability.RELIABLE_ORDERED, 0, packetData );
		
		long begin = System.currentTimeMillis();
		long now;
		
		boolean success   = true;
		boolean hasClient = false;
		boolean hasServer = false;
		
		while ( true ) {
			now = System.currentTimeMillis();
			if ( now > begin + 60000L ) {
				success = false;
				break;
			}
			
			EncapsulatedPacket packet;
			if ( !hasClient ) {
				if ( clientboundConnection == null ) {
					success = false;
					break;
				}
				if ( ( packet = clientboundConnection.receive() ) != null ) {
					if ( !Arrays.equals( packetData, packet.getPacketData() ) ) {
						success = false;
						break;
					}
					hasClient = true;
				}
			}
			if ( !hasServer ) {
				if ( serverboundConnection == null ) {
					success = false;
					break;
				}
				if ( ( packet = serverboundConnection.receive() ) != null ) {
					if ( !Arrays.equals( packetData, packet.getPacketData() ) ) {
						success = false;
						break;
					}
					hasServer = true;
				}
			}
			
			if ( hasClient && hasServer ) {
				break;
			}
		}
		
		if ( !success ) {
			throw new SocketException( "Could not send/receive from/to socket" );
		}
	}
	
	//////////////////////////////////////////////////////////////////////////
	
	private void handleSocketEvent( Socket socket, SocketEvent event ) {
		switch ( event.getType() ) {
			case NEW_INCOMING_CONNECTION:
				clientboundConnection = event.getConnection();
				break;
			
			case CONNECTION_CLOSED:
			case CONNECTION_DISCONNECTED:
				if ( clientboundConnection == event.getConnection() ) {
					clientboundConnection = null;
				} else if ( serverboundConnection == event.getConnection() ) {
					serverboundConnection = null;
				}
				break;
			
			case CONNECTION_ATTEMPT_FAILED:
				connectionAttemptComplete = true;
				break;
			
			case CONNECTION_ATTEMPT_SUCCEEDED:
				serverboundConnection = event.getConnection();
				connectionAttemptComplete = true;
				break;
		}
	}
	
	static {
		System.setProperty( "java.net.preferIPv6Addresses", "true" );
	}
	
}
