package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.CyanGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CyanGlazedTerracottaGenerator implements BlockGenerator {

   @Override
   public CyanGlazedTerracotta generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       CyanGlazedTerracotta block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public CyanGlazedTerracotta generate() {
       return new CyanGlazedTerracotta();
   }

}
