package io.gomint.jraknet;

import io.gomint.jraknet.datastructures.TriadRange;
import io.netty.buffer.ByteBuf;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PacketBuffer {

    private static final BigInteger UNSIGNED_LONG_MAX_VALUE = new BigInteger( "FFFFFFFFFFFFFFFF", 16 );
    private static final short AF_INET6 = (short) ( System.getProperty( "os.name" ).equals( "windows" ) ? 23 : 10 );

    private byte[] buffer;
    private int offset;
    private int position;


    public PacketBuffer( int capacity ) {
        this( new byte[capacity], 0 );
    }

    public PacketBuffer( byte[] buffer, int offset ) {
        this.buffer = buffer;
        this.offset = offset;
        this.position = offset;
    }

    public PacketBuffer( ByteBuf datagram ) {
        this.buffer = new byte[datagram.readableBytes()];
        datagram.readBytes( this.buffer );
        datagram.release();

        this.offset = 0;
        this.position = 0;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public int getBufferOffset() {
        return this.offset;
    }

    public int getPosition() {
        return this.position;
    }

    public int getRemaining() {
        return this.buffer.length - this.position;
    }

    public void resetPosition() {
        this.position = this.offset;
    }

    public void readBytes( byte[] v ) {
        this.ensureRemaining( v.length );
        System.arraycopy( this.buffer, this.position, v, 0, v.length );
        this.position += v.length;
    }

    private void ensureRemaining( int remaining ) {
        if ( this.position + remaining > this.buffer.length ) {
            throw new IllegalArgumentException( "Cannot read more bytes than are available" );
        }
    }

    public short readShort() {
        this.ensureRemaining( 2 );
        return (short) ( ( this.buffer[this.position++] & 0xFF ) << 8 | ( this.buffer[this.position++] & 0xFF ) );
    }

    public short readLShort() {
        this.ensureRemaining( 2 );
        return (short) ( ( this.buffer[this.position++] & 0xFF ) | ( this.buffer[this.position++] & 0xFF ) << 8 );
    }

    public float readFloat() {
        return Float.intBitsToFloat( this.readInt() );
    }

    public float readLFloat() {
        return Float.intBitsToFloat( this.readLInt() );
    }

    public int readInt() {
        this.ensureRemaining( 4 );
        return ( ( this.buffer[this.position++] & 0xFF ) << 24 |
                ( this.buffer[this.position++] & 0xFF ) << 16 |
                ( this.buffer[this.position++] & 0xFF ) << 8 |
                ( this.buffer[this.position++] & 0xFF ) );
    }

    public int readLInt() {
        this.ensureRemaining( 4 );
        return ( ( this.buffer[this.position++] & 0xFF ) |
                ( this.buffer[this.position++] & 0xFF ) << 8 |
                ( this.buffer[this.position++] & 0xFF ) << 16 |
                ( this.buffer[this.position++] & 0xFF ) << 24 );
    }

    public double readDouble() {
        return Double.longBitsToDouble( this.readLong() );
    }

    public long readLong() {
        this.ensureRemaining( 8 );
        return ( ( ( (long) this.buffer[this.position++] & 0xFF ) << 56 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 48 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 40 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 32 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 24 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 16 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 8 ) |
                ( (long) this.buffer[this.position++] & 0xFF ) );
    }

    public long readLLong() {
        this.ensureRemaining( 8 );
        return ( ( (long) this.buffer[this.position++] & 0xFF ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 8 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 16 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 24 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 32 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 40 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 48 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 56 ) );
    }

    public String readString() {
        int length = this.readUnsignedVarInt();
        this.ensureRemaining( length );
        String value = new String( this.buffer, this.position, length, StandardCharsets.UTF_8 );
        this.position += length;
        return value;
    }

    public int readUShort() {
        this.ensureRemaining( 2 );
        return ( ( this.buffer[this.position++] & 0xFF ) << 8 | ( this.buffer[this.position++] & 0xFF ) );
    }

    public void readOfflineMessageDataId() {
        this.ensureRemaining( RakNetConstraints.OFFLINE_MESSAGE_DATA_ID.length );
        this.position += RakNetConstraints.OFFLINE_MESSAGE_DATA_ID.length;
    }

    public InetSocketAddress readAddress() {
        byte ipVersion = this.readByte();
        if ( ipVersion == 4 ) {
            this.ensureRemaining( 6 );

            long complement = ~this.readUInt();

            String hostname = String.format( "%s.%s.%s.%s", String.valueOf( ( complement >> 24 ) & 0xFF ), String.valueOf( ( complement >> 16 ) & 0xFF ), String.valueOf( ( complement >> 8 ) & 0xFF ), String.valueOf( complement & 0xFF ) );
            int port = this.readUShort();

            return InetSocketAddress.createUnresolved( hostname, port );
        } else {
            // Reading sockaddr_in6 structure whose fields are _always_ in big-endian order!
            this.readUShort(); // Addressinfo
            int port = this.readUShort();
            this.readUInt(); // Flowinfo (see RFC 6437 - can safely leave it at 0)
            byte[] in6addr = new byte[16];
            this.readBytes( in6addr );
            this.readUInt(); // Scope ID

            try {
                return new InetSocketAddress( Inet6Address.getByAddress( null, in6addr, 0 ), port );
            } catch ( UnknownHostException e ) {
                throw new IllegalArgumentException( "Could not read sockaddr_in6", e );
            }
        }
    }

    public byte readByte() {
        this.ensureRemaining( 1 );
        return (byte) ( this.buffer[this.position++] & 0xFF );
    }

    public long readUInt() {
        this.ensureRemaining( 4 );
        return ( ( ( (long) this.buffer[this.position++] & 0xFF ) << 24 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 16 ) |
                ( ( (long) this.buffer[this.position++] & 0xFF ) << 8 ) |
                ( (long) this.buffer[this.position++] & 0xFF ) );
    }

    public UUID readUUID() {
        return new UUID( this.readLLong(), this.readLLong() );
    }

    public int readUnsignedVarInt() {
        int value = 0;
        int i = 0;
        int b;

        while ( ( ( b = this.readByte() ) & 0x80 ) != 0 ) {
            value |= ( b & 0x7F ) << i;
            i += 7;
            if ( i > 35 ) {
                throw new RuntimeException( "VarInt too big" );
            }
        }

        return value | ( b << i );
    }

    public int readSignedVarInt() {
        long val = readUnsignedVarLong();
        return decodeZigZag32( val );
    }

    public long readUnsignedVarLong() {
        long value = 0;
        int i = 0;
        long b;

        while ( ( ( b = this.readByte() ) & 0x80 ) != 0 ) {
            value |= ( b & 0x7F ) << i;
            i += 7;
            if ( i > 63 ) {
                throw new RuntimeException( "VerLong too big" );
            }
        }

        return value | ( b << i );
    }

    public BigInteger readSignedVarLong() {
        BigInteger val = readVarNumber( 10 );
        return decodeZigZag64( val );
    }


    private BigInteger readVarNumber( int length ) {
        BigInteger result = BigInteger.ZERO;
        int shiftOffset = 0;

        do {
            long b = (long) ( this.readByte() ) & 128L;
            if ( b == 0L ) {
                return result.or( BigInteger.valueOf( b << ( shiftOffset * 7 ) ) );
            }

            result = result.or( BigInteger.valueOf( ( b & 127L ) << shiftOffset * 7 ) );
            shiftOffset++;
        } while ( shiftOffset < length );

        throw new IllegalArgumentException( "Var Number too big" );
    }

    private long encodeZigZag32( int v ) {
        // Note:  the right-shift must be arithmetic
        return ( ( v << 1 ) ^ ( v >> 31 ) ) &  0xFFFFFFFFL;
    }

    private int decodeZigZag32( long v ) {
        return (int) ( v >> 1 ) ^ -(int) ( v & 1 );
    }

    private BigInteger encodeZigZag64( long v ) {
        BigInteger origin = BigInteger.valueOf( v );
        BigInteger left = origin.shiftLeft( 1 );
        BigInteger right = origin.shiftRight( 63 );
        return left.xor( right );
    }

    private BigInteger decodeZigZag64( BigInteger v ) {
        BigInteger left = v.shiftRight( 1 );
        BigInteger right = v.and( BigInteger.ONE ).negate();
        return left.xor( right );
    }


    public void writeUnsignedVarLong( long value ) {
        while ( ( value & -128 ) != 0 ) {
            this.writeByte( (byte) ( value & 127 | 128 ) );
            value >>>= 7;
        }

        this.writeByte( (byte) value );
    }

    public void writeSignedVarLong( long value ) {
        BigInteger signedLong = encodeZigZag64( value );
        this.writeVarBigInteger( signedLong );
    }

    public void writeUnsignedVarInt( int value ) {
        while ( (value >= 0x80) ) {
            this.writeByte( (byte) (value | 0x80) );
            value >>= 7;
        }

        this.writeByte( (byte) value );
    }

//    public void writeUnsignedVarInt( int value ) {
//        while ( ( value & -128 ) != 0 ) {
//            this.writeByte( (byte) ( value & 127 | 128 ) );
//            value >>>= 7;
//        }
//
//        this.writeByte( (byte) value );
//    }

//    public void writeUnsignedVarInt( int value ) {
//        while ( ( value & ~0x7FL ) != 0 ) {
//            this.writeByte( (byte) ( value & 0x7F | 0x80 ) );
//            value >>>= 7;
//        }
//
//        this.writeByte( (byte) value );
//    }

    public void writeSignedVarInt( int value ) {
        long signedValue = encodeZigZag32( value );
        writeUnsignedVarLong( signedValue );
    }

    private void writeVarBigInteger( BigInteger value ) {
        if ( value.compareTo( UNSIGNED_LONG_MAX_VALUE ) > 0 ) {
            throw new IllegalArgumentException( "The value is too big" );
        }

        value = value.and( UNSIGNED_LONG_MAX_VALUE );
        BigInteger i = BigInteger.valueOf( -128 );
        BigInteger x7f = BigInteger.valueOf( 0x7f );
        BigInteger x80 = BigInteger.valueOf( 0x80 );

        while ( !value.and( i ).equals( BigInteger.ZERO ) ) {
            this.writeByte( value.and( x7f ).or( x80 ).byteValue() );
            value = value.shiftRight( 7 );
        }

        this.writeByte( value.byteValue() );
    }

    public void skip( int length ) {
        this.ensureRemaining( length );
        this.position += length;
    }

    public TriadRange[] readTriadRangeList() {
        int length = this.readUShort();
        boolean isPair;
        TriadRange[] ranges = new TriadRange[length];
        int min;
        int max;

        for ( int i = 0; i < length; ++i ) {
            isPair = !this.readBoolean();
            min = this.readTriad();
            if ( isPair ) {
                max = this.readTriad();
                if ( min >= max ) {
                    return null;
                }
            } else {
                max = min;
            }
            ranges[i] = new TriadRange( min, max );
        }
        return ranges;
    }

    public boolean readBoolean() {
        return ( this.readByte() != 0x00 );
    }

    public int readTriad() {
        this.ensureRemaining( 3 );
        return ( ( this.buffer[this.position++] & 0xFF ) |
                ( this.buffer[this.position++] & 0xFF ) << 8 |
                ( this.buffer[this.position++] & 0xFF ) << 16 );
    }

    public void writeBoolean( boolean v ) {
        this.writeByte( ( v ? (byte) 0x01 : (byte) 0x00 ) );
    }

    public void writeByte( byte v ) {
        this.ensureCapacity( 1 );
        this.buffer[this.position++] = v;
    }

    private void ensureCapacity( int capacity ) {
        while ( this.position + capacity > this.buffer.length ) {
            this.reallocate( capacity );
        }
    }

    private void reallocate( int extra ) {
        byte[] nextBuffer = new byte[2 * extra + this.buffer.length];
        System.arraycopy( this.buffer, this.offset, nextBuffer, 0, this.buffer.length - this.offset );

        this.buffer = nextBuffer;
        this.position -= this.offset;
        this.offset = 0;
    }

    public void writeBytes( byte[] v, int from, int length ) {
        this.ensureCapacity( v.length );
        System.arraycopy( v, from, this.buffer, this.position, length );
        this.position += v.length;
    }

    public void writeBytes( byte[] v ) {
        this.ensureCapacity( v.length );
        System.arraycopy( v, 0, this.buffer, this.position, v.length );
        this.position += v.length;
    }

    public void writeShort( short v ) {
        this.ensureCapacity( 2 );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
    }

    public void writeLShort( short v ) {
        this.ensureCapacity( 2 );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
    }

    public void writeUInt( long v ) {
        this.ensureCapacity( 4 );
        this.buffer[this.position++] = (byte) ( ( v >> 24 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 16 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
    }

    public void writeFloat( float v ) {
        this.writeInt( Float.floatToRawIntBits( v ) );
    }

    public void writeLFloat( float v ) {
        this.writeLInt( Float.floatToRawIntBits( v ) );
    }

    public void writeInt( int v ) {
        this.ensureCapacity( 4 );
        this.buffer[this.position++] = (byte) ( ( v >> 24 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 16 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
    }

    public void writeLInt( int v ) {
        this.ensureCapacity( 4 );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 16 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 24 ) & 0xFF );
    }

    public void writeDouble( double v ) {
        this.writeLong( Double.doubleToRawLongBits( v ) );
    }

    public void writeLong( long v ) {
        this.ensureCapacity( 8 );
        this.buffer[this.position++] = (byte) ( ( v >> 56 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 48 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 40 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 32 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 24 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 16 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
    }

    public void writeLLong( long v ) {
        this.ensureCapacity( 8 );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 16 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 24 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 32 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 40 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 48 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 56 ) & 0xFF );
    }

    public void writeString( String v ) {
        byte[] ascii = v.getBytes( StandardCharsets.UTF_8 );

        this.writeUnsignedVarInt( ascii.length );
        this.ensureCapacity( ascii.length );

        System.arraycopy( ascii, 0, this.buffer, this.position, ascii.length );
        this.position += ascii.length;
    }

    public void writeUShort( int v ) {
        this.ensureCapacity( 2 );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
    }

    public void writeOfflineMessageDataId() {
        this.ensureCapacity( RakNetConstraints.OFFLINE_MESSAGE_DATA_ID.length );
        System.arraycopy( RakNetConstraints.OFFLINE_MESSAGE_DATA_ID, 0, this.buffer, this.position, RakNetConstraints.OFFLINE_MESSAGE_DATA_ID.length );
        this.position += RakNetConstraints.OFFLINE_MESSAGE_DATA_ID.length;
    }

    public void writeAddress( SocketAddress address ) {
        if ( !( address instanceof InetSocketAddress ) ) {
            throw new IllegalArgumentException( "Unknown socket address family (only AF_INET and AF_INET6 supported)" );
        }

        InetSocketAddress addr = (InetSocketAddress) address;
        if ( addr.getAddress() instanceof Inet4Address ) {
            this.ensureCapacity( 7 );
            this.writeByte( (byte) 4 );

            Inet4Address inet = (Inet4Address) addr.getAddress();
            byte[] bytes = inet.getAddress();
            long complement = ( ( (long) bytes[0] << 24 ) |
                    ( (long) bytes[1] << 16 ) |
                    ( (long) bytes[2] << 8 ) |
                    ( (long) bytes[3] ) );
            complement = ~complement;

            this.writeUInt( complement );
            this.writeUShort( addr.getPort() );
        } else if ( addr.getAddress() instanceof Inet6Address ) {
            Inet6Address in6addr = (Inet6Address) addr.getAddress();

            this.ensureCapacity( 25 );
            this.writeByte( (byte) 6 );
            this.writeUShort( AF_INET6 );
            this.writeUShort( (short) addr.getPort() );
            this.writeUInt( 0L );
            this.writeBytes( in6addr.getAddress() );
            this.writeUInt( 0L );
        }
    }

    public void writeUUID( UUID uuid ) {
        this.writeLLong( uuid.getMostSignificantBits() );
        this.writeLLong( uuid.getLeastSignificantBits() );
    }

    public void writeTriad( int v ) {
        this.ensureCapacity( 3 );
        this.buffer[this.position++] = (byte) ( v & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 8 ) & 0xFF );
        this.buffer[this.position++] = (byte) ( ( v >> 16 ) & 0xFF );
    }

    public int writeTriadRangeList( List<TriadRange> ranges, int offset, int length, int maxSize ) {
        this.ensureCapacity( 2 );

        // Reserve two bytes for the length:
        int mark = this.position;
        this.position += 2;
        maxSize -= 2;

        int count = 0;
        for ( int i = offset; i < offset + length; ++i ) {
            if ( ranges.get( i ).getMin() == ranges.get( i ).getMax() ) {
                if ( maxSize < 4 ) {
                    break;
                }

                this.writeBoolean( true );
                this.writeTriad( ranges.get( i ).getMin() );
                maxSize -= 4;
            } else {
                if ( maxSize < 7 ) {
                    break;
                }

                this.writeBoolean( false );
                this.writeTriad( ranges.get( i ).getMin() );
                this.writeTriad( ranges.get( i ).getMax() );
                maxSize -= 7;
            }
            ++count;
        }

        int pos = this.position;
        this.position = mark;
        this.writeUShort( count );
        this.position = pos;
        return count;
    }

    /**
     * Set the pointer of this buffer to the specified position
     *
     * @param position The position at which the pointer should be
     */
    public void setPosition( int position ) {
        this.position = position;
    }

    /**
     * Shrink the internal byte array to the actual size
     */
    public void shrink() {
        this.buffer = Arrays.copyOf( this.buffer, this.position );
    }

    @Override
    public String toString() {
        if ( this.buffer == null ) {
            return "null";
        }
        int iMax = this.buffer.length - 1;
        if ( iMax == -1 ) {
            return "[]";
        }

        StringBuilder b = new StringBuilder();
        b.append( '[' );
        for ( int i = 0; ; i++ ) {
            b.append( Integer.toHexString( this.buffer[i] & 0xFF ) );
            if ( i == iMax ) {
                return b.append( ']' ).toString();
            }

            b.append( ", " );
        }
    }

}
