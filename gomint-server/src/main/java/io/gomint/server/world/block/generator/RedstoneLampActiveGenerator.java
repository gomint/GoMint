package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.RedstoneLampActive;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class RedstoneLampActiveGenerator implements BlockGenerator {

   @Override
   public RedstoneLampActive generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       RedstoneLampActive block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public RedstoneLampActive generate() {
       return new RedstoneLampActive();
   }

}
