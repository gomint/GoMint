package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.type.CommandOrigin;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    public String getInputCommand() {
        return this.inputCommand;
    }

    public void setInputCommand(String inputCommand) {
        this.inputCommand = inputCommand;
    }

    public CommandOrigin getCommandOrigin() {
        return this.commandOrigin;
    }

    public void setCommandOrigin(CommandOrigin commandOrigin) {
        this.commandOrigin = commandOrigin;
    }

}
