package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.DeadBush;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class DeadBushGenerator implements BlockGenerator {

   @Override
   public DeadBush generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       DeadBush block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public DeadBush generate() {
       return new DeadBush();
   }

}
