package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondOreGenerator implements ItemGenerator {

   @Override
   public ItemDiamondOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondOre( data, amount, nbt );
   }

   @Override
   public ItemDiamondOre generate( short data, byte amount ) {
       return new ItemDiamondOre( data, amount );
   }

}
