package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPrismarine;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPrismarineGenerator implements ItemGenerator {

   @Override
   public ItemPrismarine generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPrismarine( data, amount, nbt );
   }

   @Override
   public ItemPrismarine generate( short data, byte amount ) {
       return new ItemPrismarine( data, amount );
   }

}
