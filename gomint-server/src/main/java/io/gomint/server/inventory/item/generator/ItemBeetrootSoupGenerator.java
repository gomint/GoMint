package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBeetrootSoup;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBeetrootSoupGenerator implements ItemGenerator {

   @Override
   public ItemBeetrootSoup generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBeetrootSoup( data, amount, nbt );
   }

   @Override
   public ItemBeetrootSoup generate( short data, byte amount ) {
       return new ItemBeetrootSoup( data, amount );
   }

}
