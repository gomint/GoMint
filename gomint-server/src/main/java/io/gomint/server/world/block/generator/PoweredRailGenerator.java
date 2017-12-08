package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.PoweredRail;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PoweredRailGenerator implements BlockGenerator {

   @Override
   public PoweredRail generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       PoweredRail block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public PoweredRail generate() {
       return new PoweredRail();
   }

}
