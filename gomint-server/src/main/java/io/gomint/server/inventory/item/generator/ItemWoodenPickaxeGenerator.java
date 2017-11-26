package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenPickaxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenPickaxeGenerator implements ItemGenerator {

   @Override
   public ItemWoodenPickaxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenPickaxe( data, amount, nbt );
   }

   @Override
   public ItemWoodenPickaxe generate( short data, byte amount ) {
       return new ItemWoodenPickaxe( data, amount );
   }

}
