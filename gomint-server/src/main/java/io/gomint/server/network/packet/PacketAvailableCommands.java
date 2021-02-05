package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.type.CommandData;
import io.gomint.server.util.collection.IndexedHashMap;

import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
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
            buffer.writeString( data.name() );
            buffer.writeString( data.description() );

            // Flags?
            buffer.writeByte( data.flags() );
            buffer.writeByte( data.permission() );

            // Alias enum index
            buffer.writeLInt( data.aliasIndex() );

            // Write parameters and overload
            buffer.writeUnsignedVarInt( data.parameters().size() );
            for ( List<CommandData.Parameter> parameters : data.parameters() ) {
                buffer.writeUnsignedVarInt( parameters.size() );
                for ( CommandData.Parameter parameter : parameters ) {
                    buffer.writeString( parameter.name() );
                    buffer.writeLInt( parameter.type() );
                    buffer.writeBoolean( parameter.optional() );
                    buffer.writeByte((byte) 0); // TODO: Find out what this really is
                }
            }
        }

        // TODO: soft enums
        buffer.writeUnsignedVarInt( 0 );
        buffer.writeUnsignedVarInt( 0 );
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

    private int readEnumIndex( PacketBuffer buffer ) {
        if ( this.enumValues.size() < 256 ) {
            return buffer.readByte() & 0xFF;
        } else if ( this.enumValues.size() < 65536 ) {
            return buffer.readLShort() & 0xFFFF;
        } else {
            return buffer.readLInt();
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public List<String> getEnumValues() {
        return this.enumValues;
    }

    public void setEnumValues(List<String> enumValues) {
        this.enumValues = enumValues;
    }

    public List<String> getPostFixes() {
        return this.postFixes;
    }

    public void setPostFixes(List<String> postFixes) {
        this.postFixes = postFixes;
    }

    public IndexedHashMap<String, List<Integer>> getEnums() {
        return this.enums;
    }

    public void setEnums(IndexedHashMap<String, List<Integer>> enums) {
        this.enums = enums;
    }

    public List<CommandData> getCommandData() {
        return this.commandData;
    }

    public void setCommandData(List<CommandData> commandData) {
        this.commandData = commandData;
    }

}
