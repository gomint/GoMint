package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.SnowLayer;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SnowLayerGenerator implements BlockGenerator {

   @Override
   public SnowLayer generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       SnowLayer block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public SnowLayer generate() {
       return new SnowLayer();
   }

}
