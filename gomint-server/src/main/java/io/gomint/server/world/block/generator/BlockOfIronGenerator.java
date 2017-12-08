package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.BlockOfIron;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockOfIronGenerator implements BlockGenerator {

   @Override
   public BlockOfIron generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       BlockOfIron block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public BlockOfIron generate() {
       return new BlockOfIron();
   }

}
