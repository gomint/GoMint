package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.UpdateGameBlockUpdate2;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class UpdateGameBlockUpdate2Generator implements BlockGenerator {

   @Override
   public UpdateGameBlockUpdate2 generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       UpdateGameBlockUpdate2 block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public UpdateGameBlockUpdate2 generate() {
       return new UpdateGameBlockUpdate2();
   }

}
