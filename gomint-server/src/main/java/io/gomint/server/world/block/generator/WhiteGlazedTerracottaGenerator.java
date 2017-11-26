package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.WhiteGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class WhiteGlazedTerracottaGenerator implements BlockGenerator {

   @Override
   public WhiteGlazedTerracotta generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       WhiteGlazedTerracotta block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public WhiteGlazedTerracotta generate() {
       return new WhiteGlazedTerracotta();
   }

}
