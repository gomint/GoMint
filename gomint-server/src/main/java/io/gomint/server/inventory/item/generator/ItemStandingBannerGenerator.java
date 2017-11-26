package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStandingBanner;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStandingBannerGenerator implements ItemGenerator {

   @Override
   public ItemStandingBanner generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStandingBanner( data, amount, nbt );
   }

   @Override
   public ItemStandingBanner generate( short data, byte amount ) {
       return new ItemStandingBanner( data, amount );
   }

}
