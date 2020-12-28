/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.test.network;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.packet.PacketTileEntityData;
import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTTagCompound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CheckPacketTileEntityTest {

    @Test
    public void checkNBTAllocLimitFailure() throws Exception {
        Assertions.assertThrows(AllocationLimitReachedException.class, () -> {
            // Fill new compound with too much data for the packet
            NBTTagCompound testCompound = new NBTTagCompound( "" );
            List<String> stuff = new ArrayList<>();

            for ( int i = 0; i < 512 * 1024; i++ ) {
                stuff.add(String.valueOf(i));
            }

            testCompound.addValue( "TEST", stuff );

            // Create the packet
            PacketTileEntityData packetTileEntityData = new PacketTileEntityData();
            packetTileEntityData.setPosition( new BlockPosition( 0, 1, 0 ) );
            packetTileEntityData.setCompound( testCompound );

            // Serialize the packet
            PacketBuffer buffer = new PacketBuffer( 16 );
            packetTileEntityData.serialize( buffer, Protocol.MINECRAFT_PE_PROTOCOL_VERSION );

            // Deserialize the packet, this should raise a exception
            packetTileEntityData = new PacketTileEntityData();
            packetTileEntityData.deserialize( buffer, Protocol.MINECRAFT_PE_PROTOCOL_VERSION );
        });
    }

    @Test
    public void checkNBTAllocLimitSuccess() throws Exception {
        // Fill new compound with too much data for the packet
        NBTTagCompound testCompound = new NBTTagCompound( "" );
        for ( int i = 0; i < 4; i++ ) {
            testCompound.addValue( String.valueOf( i ), new ArrayList() );
        }

        // Create the packet
        PacketTileEntityData packetTileEntityData = new PacketTileEntityData();
        packetTileEntityData.setPosition( new BlockPosition( 0, 1, 0 ) );
        packetTileEntityData.setCompound( testCompound );

        // Serialize the packet
        PacketBuffer buffer = new PacketBuffer( 16 );
        packetTileEntityData.serialize( buffer, Protocol.MINECRAFT_PE_PROTOCOL_VERSION );

        // Deserialize the packet, this should raise a exception
        packetTileEntityData = new PacketTileEntityData();
        packetTileEntityData.deserialize( buffer, Protocol.MINECRAFT_PE_PROTOCOL_VERSION );
    }

}
