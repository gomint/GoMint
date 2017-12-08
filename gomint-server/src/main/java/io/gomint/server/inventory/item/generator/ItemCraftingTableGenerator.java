package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCraftingTable;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCraftingTableGenerator implements ItemGenerator {

   @Override
   public ItemCraftingTable generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCraftingTable( data, amount, nbt );
   }

   @Override
   public ItemCraftingTable generate( short data, byte amount ) {
       return new ItemCraftingTable( data, amount );
   }

}
