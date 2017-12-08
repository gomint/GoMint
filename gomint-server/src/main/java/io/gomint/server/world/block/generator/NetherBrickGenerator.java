package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.NetherBrick;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class NetherBrickGenerator implements BlockGenerator {

   @Override
   public NetherBrick generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       NetherBrick block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public NetherBrick generate() {
       return new NetherBrick();
   }

}
