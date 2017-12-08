package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWallBanner;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWallBannerGenerator implements ItemGenerator {

   @Override
   public ItemWallBanner generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWallBanner( data, amount, nbt );
   }

   @Override
   public ItemWallBanner generate( short data, byte amount ) {
       return new ItemWallBanner( data, amount );
   }

}
