package io.gomint.server.network.handler;

import io.gomint.event.world.SignChangeEvent;
import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketTileEntityData;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.Sign;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockSign;

import java.util.Arrays;
import java.util.List;

/**
 * @author Kevims
 * @version 1.0
 */
public class PacketTileEntityDataHandler implements PacketHandler<PacketTileEntityData> {

    @Override
    public void handle( PacketTileEntityData packet, long currentTimeMillis, PlayerConnection connection ) {
        Block block = connection.getEntity().getWorld().getBlockAt( packet.getPosition() );
        TileEntity tileEntity = block.getTileEntity();

        if ( tileEntity != null ) {
            if ( block instanceof BlockSign && tileEntity instanceof SignTileEntity ) {
                NBTTagCompound compound = packet.getCompound();
                SignTileEntity signTileEntity = (SignTileEntity) tileEntity;

                if ( compound.containsKey( "Text" ) ) {
                    String text = compound.getString( "Text", "" );
                    List<String> lines = Arrays.asList( text.split( "\n" ) );
                    SignChangeEvent signChangeEvent = connection.getServer().getPluginManager().callEvent( new SignChangeEvent( connection.getEntity(), (BlockSign) block, lines ) );

                    if ( !signChangeEvent.isCancelled() ) {
                        compound.addValue( "Text", SignTileEntity.CONTENT_JOINER.join( lines ) );
                        signTileEntity.updateCompound( compound );
                    }
                }
            }
        }
    }

}
