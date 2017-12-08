package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondHoe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondHoeGenerator implements ItemGenerator {

   @Override
   public ItemDiamondHoe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondHoe( data, amount, nbt );
   }

   @Override
   public ItemDiamondHoe generate( short data, byte amount ) {
       return new ItemDiamondHoe( data, amount );
   }

}
