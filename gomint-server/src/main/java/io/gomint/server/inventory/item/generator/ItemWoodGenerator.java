package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWood;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodGenerator implements ItemGenerator {

   @Override
   public ItemWood generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWood( data, amount, nbt );
   }

   @Override
   public ItemWood generate( short data, byte amount ) {
       return new ItemWood( data, amount );
   }

}
