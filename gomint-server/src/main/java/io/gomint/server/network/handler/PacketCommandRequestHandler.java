package io.gomint.server.network.handler;

import com.google.common.base.Joiner;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandOutputMessage;
import io.gomint.command.CommandOverload;
import io.gomint.command.ParamValidator;
import io.gomint.server.command.CommandHolder;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketCommandOutput;
import io.gomint.server.network.packet.PacketCommandRequest;
import io.gomint.server.network.type.CommandOrigin;
import io.gomint.server.network.type.OutputMessage;

import java.util.*;

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

        // Search for correct command holder
        String removedFirstChar = packet.getInputCommand().substring( 1 );
        CommandHolder selected = null;
        for ( CommandHolder commandHolder : connection.getServer().getPluginManager().getCommandManager().getCommands() ) {
            if ( removedFirstChar.startsWith( commandHolder.getName() ) ) {
                if ( selected == null || selected.getName().length() < commandHolder.getName().length() ) {
                    selected = commandHolder;
                }
            }
        }

        // Check if we selected a command
        if ( selected == null ) {
            // Send CommandOutput with failure
            sendFailure( packet.getCommandOrigin(), connection, new ArrayList<OutputMessage>() {{
                add( new OutputMessage( "Command for input '%%s' could not be found", false, new ArrayList<String>() {{
                    add( packet.getInputCommand() );
                }} ) );
            }} );
        } else {
            // Check for permission
            if ( selected.getPermission() != null && !connection.getEntity().hasPermission( selected.getPermission() ) ) {
                sendFailure( packet.getCommandOrigin(), connection, new ArrayList<OutputMessage>() {{
                    add( new OutputMessage( "No permission for this command", false, new ArrayList<>() ) );
                }} );
            } else {
                // Now we need to parse all additional parameters
                String restData;
                if ( removedFirstChar.length() > selected.getName().length() ) {
                    restData = removedFirstChar.substring( selected.getName().length() + 1 );
                } else {
                    restData = "";
                }

                String[] params = !restData.isEmpty() ? restData.split( " " ) : new String[0];

                PacketCommandOutput output = new PacketCommandOutput();
                packet.getCommandOrigin().setType( (byte) 3 );
                output.setOrigin( packet.getCommandOrigin() );
                CommandOutput commandOutput = null;

                if ( selected.getOverload() != null ) {
                    overloads:
                    for ( CommandOverload overload : selected.getOverload() ) {
                        Iterator<String> paramIterator = Arrays.asList( params ).iterator();

                        if ( overload.getParameters() != null ) {
                            Map<String, Object> commandInput = new HashMap<>();

                            for ( Map.Entry<String, ParamValidator> entry : overload.getParameters().entrySet() ) {
                                List<String> input = new ArrayList<>();
                                ParamValidator validator = entry.getValue();

                                if ( validator.consumesParts() > 0 ) {
                                    for ( int i = 0; i < validator.consumesParts(); i++ ) {
                                        if ( !paramIterator.hasNext() ) {
                                            if ( !validator.isOptional() ) {
                                                output.setSuccess( false );
                                                output.setOutputs( new ArrayList<OutputMessage>() {{
                                                    add( new OutputMessage( "Not enough parameters for command '%%s'", false, new ArrayList<String>() {{
                                                        add( packet.getInputCommand() );
                                                    }} ) );
                                                }} );

                                                continue overloads;
                                            }
                                        } else {
                                            input.add( paramIterator.next() );
                                        }
                                    }
                                } else {
                                    // Consume as much as possible for thing like TEXT, RAWTEXT
                                    while ( paramIterator.hasNext() ) {
                                        input.add( paramIterator.next() );
                                    }
                                }

                                if ( input.size() == validator.consumesParts() || validator.consumesParts() < 0 ) {
                                    Object result = validator.validate( input, connection.getEntity() );
                                    if ( result == null ) {
                                        output.setSuccess( false );
                                        output.setOutputs( new ArrayList<OutputMessage>() {{
                                            add( new OutputMessage( "Validation of parameter '%%s' from input '%%s' failed", false, new ArrayList<String>() {{
                                                add( Joiner.on( ", " ).join( input ) );
                                                add( packet.getInputCommand() );
                                            }} ) );
                                        }} );

                                        continue overloads;
                                    }

                                    commandInput.put( entry.getKey(), result );
                                }
                            }

                            if ( !paramIterator.hasNext() ) {
                                commandOutput = selected.getExecutor().execute( connection.getEntity(), selected.getName(), commandInput );
                                break;
                            }
                        }
                    }

                    if ( commandOutput == null && output.isSuccess() ) {
                        commandOutput = selected.getExecutor().execute( connection.getEntity(), selected.getName(), new HashMap<>() );
                    }
                } else {
                    commandOutput = selected.getExecutor().execute( connection.getEntity(), selected.getName(), new HashMap<>() );
                }

                if ( commandOutput != null ) {
                    output.setSuccess( commandOutput.isSuccess() );

                    // Remap outputs
                    List<OutputMessage> outputMessages = new ArrayList<>();
                    for ( CommandOutputMessage commandOutputMessage : commandOutput.getMessages() ) {
                        outputMessages.add( new OutputMessage( commandOutputMessage.getFormat(), commandOutputMessage.isSuccess(), commandOutputMessage.getParameters() ) );
                    }

                    output.setOutputs( outputMessages );
                }

                connection.send( output );
            }
        }
    }

    private void sendFailure( CommandOrigin origin, PlayerConnection connection, List<OutputMessage> messages ) {
        PacketCommandOutput output = new PacketCommandOutput();
        origin.setType( (byte) 3 );
        output.setOrigin( origin );
        output.setSuccess( false );
        output.setOutputs( messages );
        connection.addToSendQueue( output );
    }

}
