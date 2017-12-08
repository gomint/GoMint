package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.StandingBanner;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StandingBannerGenerator implements BlockGenerator {

   @Override
   public StandingBanner generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       StandingBanner block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public StandingBanner generate() {
       return new StandingBanner();
   }

}
