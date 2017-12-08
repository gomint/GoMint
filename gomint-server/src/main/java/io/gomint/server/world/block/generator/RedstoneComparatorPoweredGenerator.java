package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.RedstoneComparatorPowered;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class RedstoneComparatorPoweredGenerator implements BlockGenerator {

   @Override
   public RedstoneComparatorPowered generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       RedstoneComparatorPowered block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public RedstoneComparatorPowered generate() {
       return new RedstoneComparatorPowered();
   }

}
