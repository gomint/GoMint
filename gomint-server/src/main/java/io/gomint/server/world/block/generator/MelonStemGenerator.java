package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.MelonStem;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class MelonStemGenerator implements BlockGenerator {

   @Override
   public MelonStem generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       MelonStem block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public MelonStem generate() {
       return new MelonStem();
   }

}
