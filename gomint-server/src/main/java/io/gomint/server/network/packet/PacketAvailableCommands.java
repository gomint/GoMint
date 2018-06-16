package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.type.CommandData;
import io.gomint.server.util.collection.IndexedHashMap;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketAvailableCommands extends Packet {

    private List<String> enumValues;
    private List<String> postFixes;
    private IndexedHashMap<String, List<Integer>> enums;
    private List<CommandData> commandData;

    /**
     * Construct a new packet
     */
    public PacketAvailableCommands() {
        super( Protocol.PACKET_AVAILABLE_COMMANDS );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        // First we need to write all enum values
        buffer.writeUnsignedVarInt( this.enumValues.size() );
        for ( String enumValue : this.enumValues ) {
            buffer.writeString( enumValue );
        }

        // After that we write all postfix data
        buffer.writeUnsignedVarInt( this.postFixes.size() );
        for ( String postFix : this.postFixes ) {
            buffer.writeString( postFix );
        }

        // Now we need to write enum index value data
        buffer.writeUnsignedVarInt( this.enums.size() );
        for ( Map.Entry<String, List<Integer>> entry : this.enums.entrySet() ) {
            buffer.writeString( entry.getKey() );
            buffer.writeUnsignedVarInt( entry.getValue().size() );
            for ( Integer enumValueIndex : entry.getValue() ) {
                writeEnumIndex( enumValueIndex, buffer );
            }
        }

        // Now write command data
        buffer.writeUnsignedVarInt( this.commandData.size() );
        for ( CommandData data : this.commandData ) {
            // Command meta
            buffer.writeString( data.getName() );
            buffer.writeString( data.getDescription() );

            // Flags?
            buffer.writeByte( data.getFlags() );
            buffer.writeByte( data.getPermission() );

            // Alias enum index
            buffer.writeLInt( -1 );     // TODO: Aliases are broken in 1.2, we fix this by taking each alias as seperate command

            // Write parameters and overload
            buffer.writeUnsignedVarInt( data.getParameters().size() );
            for ( List<CommandData.Parameter> parameters : data.getParameters() ) {
                buffer.writeUnsignedVarInt( parameters.size() );
                for ( CommandData.Parameter parameter : parameters ) {
                    buffer.writeString( parameter.getName() );
                    buffer.writeLInt( parameter.getType() );
                    buffer.writeBoolean( parameter.isOptional() );
                }
            }
        }
    }

    private void writeEnumIndex( int enumValueIndex, PacketBuffer buffer ) {
        if ( this.enumValues.size() < 256 ) {
            buffer.writeByte( (byte) enumValueIndex );
        } else if ( this.enumValues.size() < 65536 ) {
            buffer.writeLShort( (short) enumValueIndex );
        } else {
            buffer.writeLInt( enumValueIndex );
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
