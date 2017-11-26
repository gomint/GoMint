package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.UpdateGameBlockUpdate1;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class UpdateGameBlockUpdate1Generator implements BlockGenerator {

   @Override
   public UpdateGameBlockUpdate1 generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       UpdateGameBlockUpdate1 block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public UpdateGameBlockUpdate1 generate() {
       return new UpdateGameBlockUpdate1();
   }

}
