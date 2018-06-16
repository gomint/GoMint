package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStonePickaxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStonePickaxeGenerator implements ItemGenerator {

   @Override
   public ItemStonePickaxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStonePickaxe( data, amount, nbt );
   }

   @Override
   public ItemStonePickaxe generate( short data, byte amount ) {
       return new ItemStonePickaxe( data, amount );
   }

}
