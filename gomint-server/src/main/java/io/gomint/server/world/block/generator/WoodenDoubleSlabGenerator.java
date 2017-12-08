package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.WoodenDoubleSlab;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class WoodenDoubleSlabGenerator implements BlockGenerator {

   @Override
   public WoodenDoubleSlab generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       WoodenDoubleSlab block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public WoodenDoubleSlab generate() {
       return new WoodenDoubleSlab();
   }

}
