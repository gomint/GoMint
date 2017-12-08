package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.InvertedDaylightSensor;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class InvertedDaylightSensorGenerator implements BlockGenerator {

   @Override
   public InvertedDaylightSensor generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       InvertedDaylightSensor block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public InvertedDaylightSensor generate() {
       return new InvertedDaylightSensor();
   }

}
