package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.ShulkerBox;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ShulkerBoxGenerator implements BlockGenerator {

   @Override
   public ShulkerBox generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       ShulkerBox block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public ShulkerBox generate() {
       return new ShulkerBox();
   }

}
