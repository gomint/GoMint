package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.LapisLazuliOre;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LapisLazuliOreGenerator implements BlockGenerator {

   @Override
   public LapisLazuliOre generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       LapisLazuliOre block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public LapisLazuliOre generate() {
       return new LapisLazuliOre();
   }

}
