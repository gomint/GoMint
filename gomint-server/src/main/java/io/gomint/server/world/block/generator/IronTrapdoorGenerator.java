package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.IronTrapdoor;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class IronTrapdoorGenerator implements BlockGenerator {

   @Override
   public IronTrapdoor generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       IronTrapdoor block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public IronTrapdoor generate() {
       return new IronTrapdoor();
   }

}
