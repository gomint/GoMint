package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStoneHoe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneHoeGenerator implements ItemGenerator {

   @Override
   public ItemStoneHoe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStoneHoe( data, amount, nbt );
   }

   @Override
   public ItemStoneHoe generate( short data, byte amount ) {
       return new ItemStoneHoe( data, amount );
   }

}
