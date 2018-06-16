package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBanner;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBannerGenerator implements ItemGenerator {

   @Override
   public ItemBanner generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBanner( data, amount, nbt );
   }

   @Override
   public ItemBanner generate( short data, byte amount ) {
       return new ItemBanner( data, amount );
   }

}
