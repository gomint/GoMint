package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondPickaxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondPickaxeGenerator implements ItemGenerator {

   @Override
   public ItemDiamondPickaxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondPickaxe( data, amount, nbt );
   }

   @Override
   public ItemDiamondPickaxe generate( short data, byte amount ) {
       return new ItemDiamondPickaxe( data, amount );
   }

}
