package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.SandstoneStairs;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SandstoneStairsGenerator implements BlockGenerator {

   @Override
   public SandstoneStairs generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       SandstoneStairs block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public SandstoneStairs generate() {
       return new SandstoneStairs();
   }

}
