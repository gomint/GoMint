package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronPickaxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronPickaxeGenerator implements ItemGenerator {

   @Override
   public ItemIronPickaxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronPickaxe( data, amount, nbt );
   }

   @Override
   public ItemIronPickaxe generate( short data, byte amount ) {
       return new ItemIronPickaxe( data, amount );
   }

}
