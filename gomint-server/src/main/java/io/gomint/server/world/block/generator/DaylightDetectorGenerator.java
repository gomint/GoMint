package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.DaylightDetector;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class DaylightDetectorGenerator implements BlockGenerator {

   @Override
   public DaylightDetector generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       DaylightDetector block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public DaylightDetector generate() {
       return new DaylightDetector();
   }

}
