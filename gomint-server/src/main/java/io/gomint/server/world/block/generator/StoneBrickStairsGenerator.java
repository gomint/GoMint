package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.StoneBrickStairs;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StoneBrickStairsGenerator implements BlockGenerator {

   @Override
   public StoneBrickStairs generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       StoneBrickStairs block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public StoneBrickStairs generate() {
       return new StoneBrickStairs();
   }

}
