package io.gomint.server.maintenance.performance.protocol;

import lombok.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class StartNewTickPacket extends Packet {

    private long startInNanos;

    @Override
    public void serialize( DataOutputStream dataOutputStream ) throws IOException {
        dataOutputStream.writeLong( this.startInNanos );
    }

    @Override
    public void deserialize( DataInputStream dataInputStream ) throws IOException {
        this.startInNanos = dataInputStream.readLong();
    }

}
