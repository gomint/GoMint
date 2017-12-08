package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.ItemFrame;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFrameGenerator implements BlockGenerator {

   @Override
   public ItemFrame generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       ItemFrame block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public ItemFrame generate() {
       return new ItemFrame();
   }

}
