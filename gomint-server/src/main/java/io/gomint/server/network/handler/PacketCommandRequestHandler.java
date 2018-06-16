package io.gomint.server.network.handler;

import io.gomint.command.CommandOutput;
import io.gomint.command.CommandOutputMessage;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketCommandOutput;
import io.gomint.server.network.packet.PacketCommandRequest;
import io.gomint.server.network.type.OutputMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketCommandRequestHandler implements PacketHandler<PacketCommandRequest> {

    @Override
    public void handle( PacketCommandRequest packet, long currentTimeMillis, PlayerConnection connection ) {
        // Sanity stuff
        if ( !packet.getInputCommand().startsWith( "/" ) ) {
            return;
        }

        CommandOutput commandOutput = connection.getEntity().dispatchCommand( packet.getInputCommand() );

        PacketCommandOutput packetCommandOutput = new PacketCommandOutput();
        packetCommandOutput.setSuccess( commandOutput.isSuccess() );
        packetCommandOutput.setOrigin( packet.getCommandOrigin().setType( (byte) 3 ) );

        // Remap outputs
        List<OutputMessage> outputMessages = new ArrayList<>();
        for ( CommandOutputMessage commandOutputMessage : commandOutput.getMessages() ) {
            outputMessages.add( new OutputMessage( commandOutputMessage.getFormat(), commandOutputMessage.isSuccess(), commandOutputMessage.getParameters() ) );
        }

        packetCommandOutput.setOutputs( outputMessages );
        connection.addToSendQueue( packetCommandOutput );
    }

}
