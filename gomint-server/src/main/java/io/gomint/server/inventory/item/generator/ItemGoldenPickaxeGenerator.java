package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenPickaxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenPickaxeGenerator implements ItemGenerator {

   @Override
   public ItemGoldenPickaxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenPickaxe( data, amount, nbt );
   }

   @Override
   public ItemGoldenPickaxe generate( short data, byte amount ) {
       return new ItemGoldenPickaxe( data, amount );
   }

}
