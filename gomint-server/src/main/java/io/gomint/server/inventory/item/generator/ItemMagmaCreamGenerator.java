package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMagmaCream;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMagmaCreamGenerator implements ItemGenerator {

   @Override
   public ItemMagmaCream generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMagmaCream( data, amount, nbt );
   }

   @Override
   public ItemMagmaCream generate( short data, byte amount ) {
       return new ItemMagmaCream( data, amount );
   }

}
