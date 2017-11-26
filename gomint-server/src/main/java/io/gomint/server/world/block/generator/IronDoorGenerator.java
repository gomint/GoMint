package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.IronDoor;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class IronDoorGenerator implements BlockGenerator {

   @Override
   public IronDoor generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       IronDoor block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public IronDoor generate() {
       return new IronDoor();
   }

}
