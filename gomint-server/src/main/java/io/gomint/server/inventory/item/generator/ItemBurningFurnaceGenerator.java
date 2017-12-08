package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBurningFurnace;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBurningFurnaceGenerator implements ItemGenerator {

   @Override
   public ItemBurningFurnace generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBurningFurnace( data, amount, nbt );
   }

   @Override
   public ItemBurningFurnace generate( short data, byte amount ) {
       return new ItemBurningFurnace( data, amount );
   }

}
