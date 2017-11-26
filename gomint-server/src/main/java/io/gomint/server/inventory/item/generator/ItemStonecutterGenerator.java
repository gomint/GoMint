package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStonecutter;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStonecutterGenerator implements ItemGenerator {

   @Override
   public ItemStonecutter generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStonecutter( data, amount, nbt );
   }

   @Override
   public ItemStonecutter generate( short data, byte amount ) {
       return new ItemStonecutter( data, amount );
   }

}
