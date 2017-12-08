package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRawChicken;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRawChickenGenerator implements ItemGenerator {

   @Override
   public ItemRawChicken generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRawChicken( data, amount, nbt );
   }

   @Override
   public ItemRawChicken generate( short data, byte amount ) {
       return new ItemRawChicken( data, amount );
   }

}
