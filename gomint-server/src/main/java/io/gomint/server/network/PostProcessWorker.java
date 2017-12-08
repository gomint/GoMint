package io.gomint.server.network;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PostProcessWorker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger( PostProcessWorker.class );

    private static final ThreadLocal<BatchStreamHolder> BATCH_HOLDER = new ThreadLocal<>();
    private final PlayerConnection connection;
    private final Packet[] packets;

    public PostProcessWorker( PlayerConnection connection, Packet[] packets ) {
        this.connection = connection;
        this.packets = packets;
    }

    private BatchStreamHolder getHolder() {
        if ( BATCH_HOLDER.get() == null ) {
            BatchStreamHolder holder = new BatchStreamHolder();
            BATCH_HOLDER.set( holder );
            return holder;
        }

        return BATCH_HOLDER.get();
    }

    @Override
    public void run() {
        BatchStreamHolder holder = this.getHolder();

        // Batch them first
        for ( Packet packet : this.packets ) {
            PacketBuffer buffer = new PacketBuffer( 64 );
            buffer.writeByte( packet.getId() );
            buffer.writeShort( (short) 0 );
            packet.serialize( buffer );

            try {
                writeVarInt( buffer.getPosition(), holder.getOutputStream() );
                holder.getOutputStream().write( buffer.getBuffer(), buffer.getBufferOffset(), buffer.getPosition() - buffer.getBufferOffset() );
            } catch ( IOException e ) {
                LOGGER.error( "Could not write packet data into batch: ", e );
            }
        }

        PacketBatch batch = new PacketBatch();
        batch.setPayload( holder.getBytes() );

        EncryptionHandler encryptionHandler = this.connection.getEncryptionHandler();
        if ( encryptionHandler != null && ( this.connection.getState() == PlayerConnectionState.LOGIN || this.connection.getState() == PlayerConnectionState.PLAYING ) ) {
            batch.setPayload( encryptionHandler.encryptInputForClient( batch.getPayload() ) );
        }

        holder.reset();
        this.connection.send( batch );
    }

    private void writeVarInt( int value, OutputStream stream ) throws IOException {
        int copyValue = value;

        while ( ( copyValue & -128 ) != 0 ) {
            stream.write( copyValue & 127 | 128 );
            copyValue >>>= 7;
        }

        stream.write( copyValue );
    }

    private final class BatchStreamHolder {

        private ByteArrayOutputStream bout;
        private final Deflater deflater;

        private BatchStreamHolder() {
            this.bout = new ByteArrayOutputStream();
            this.deflater = new Deflater();
        }

        private void reset() {
            this.bout = new ByteArrayOutputStream();
            this.deflater.reset();
            this.deflater.setInput( new byte[0] );
        }

        private OutputStream getOutputStream() {
            return this.bout;
        }

        private byte[] getBytes() {
            byte[] input = this.bout.toByteArray();
            this.deflater.setInput( input );
            this.deflater.finish();

            this.bout.reset();
            byte[] intermediate = new byte[1024];
            while ( !this.deflater.finished() ) {
                int read = this.deflater.deflate( intermediate );
                this.bout.write( intermediate, 0, read );
            }

            return this.bout.toByteArray();
        }

    }

}
