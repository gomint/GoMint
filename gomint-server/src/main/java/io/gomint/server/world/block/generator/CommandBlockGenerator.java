package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.CommandBlock;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandBlockGenerator implements BlockGenerator {

   @Override
   public CommandBlock generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       CommandBlock block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public CommandBlock generate() {
       return new CommandBlock();
   }

}
