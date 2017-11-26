package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.RedstoneRepeaterActive;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class RedstoneRepeaterActiveGenerator implements BlockGenerator {

   @Override
   public RedstoneRepeaterActive generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       RedstoneRepeaterActive block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public RedstoneRepeaterActive generate() {
       return new RedstoneRepeaterActive();
   }

}
