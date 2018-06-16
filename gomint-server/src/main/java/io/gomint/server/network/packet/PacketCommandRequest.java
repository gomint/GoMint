package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.type.CommandOrigin;
import io.gomint.server.util.DumpUtil;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketCommandRequest extends Packet {

    private String inputCommand;
    private CommandOrigin commandOrigin;

    /**
     * Construct a new packet
     */
    public PacketCommandRequest() {
        super( Protocol.PACKET_COMMAND_REQUEST );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.inputCommand = buffer.readString();
        this.commandOrigin = readCommandOrigin( buffer );
    }

}
