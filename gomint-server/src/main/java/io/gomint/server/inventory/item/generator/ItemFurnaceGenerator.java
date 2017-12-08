package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFurnace;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFurnaceGenerator implements ItemGenerator {

   @Override
   public ItemFurnace generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFurnace( data, amount, nbt );
   }

   @Override
   public ItemFurnace generate( short data, byte amount ) {
       return new ItemFurnace( data, amount );
   }

}
