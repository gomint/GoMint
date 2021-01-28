package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.type.CommandOrigin;
import io.gomint.server.network.type.OutputMessage;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketCommandOutput extends Packet {

    private CommandOrigin origin;
    private boolean success = true;
    private List<OutputMessage> outputs;

    /**
     * Construct a new packet
     */
    public PacketCommandOutput() {
        super( Protocol.PACKET_COMMAND_OUTPUT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        writeCommandOrigin( this.origin, buffer );
        buffer.writeBoolean( this.success );
        buffer.writeUnsignedVarInt( this.outputs.size() );
        for ( OutputMessage output : this.outputs ) {
            buffer.writeBoolean( output.success() );
            buffer.writeString( output.format() );
            buffer.writeUnsignedVarInt( output.parameters().size() );
            for ( String s : output.parameters() ) {
                buffer.writeString( s );
            }
        }

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public CommandOrigin getOrigin() {
        return this.origin;
    }

    public void setOrigin(CommandOrigin origin) {
        this.origin = origin;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OutputMessage> getOutputs() {
        return this.outputs;
    }

    public void setOutputs(List<OutputMessage> outputs) {
        this.outputs = outputs;
    }
}
