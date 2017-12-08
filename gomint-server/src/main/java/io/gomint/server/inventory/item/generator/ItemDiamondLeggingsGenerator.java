package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondLeggings;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondLeggingsGenerator implements ItemGenerator {

   @Override
   public ItemDiamondLeggings generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondLeggings( data, amount, nbt );
   }

   @Override
   public ItemDiamondLeggings generate( short data, byte amount ) {
       return new ItemDiamondLeggings( data, amount );
   }

}
