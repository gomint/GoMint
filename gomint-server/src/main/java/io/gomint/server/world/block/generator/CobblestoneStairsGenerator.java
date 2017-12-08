package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.CobblestoneStairs;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CobblestoneStairsGenerator implements BlockGenerator {

   @Override
   public CobblestoneStairs generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       CobblestoneStairs block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public CobblestoneStairs generate() {
       return new CobblestoneStairs();
   }

}
