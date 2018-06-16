package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondBoots;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondBootsGenerator implements ItemGenerator {

   @Override
   public ItemDiamondBoots generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondBoots( data, amount, nbt );
   }

   @Override
   public ItemDiamondBoots generate( short data, byte amount ) {
       return new ItemDiamondBoots( data, amount );
   }

}
