package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.ActivatorRail;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ActivatorRailGenerator implements BlockGenerator {

   @Override
   public ActivatorRail generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       ActivatorRail block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public ActivatorRail generate() {
       return new ActivatorRail();
   }

}
