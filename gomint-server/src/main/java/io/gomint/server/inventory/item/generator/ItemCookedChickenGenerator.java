package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCookedChicken;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCookedChickenGenerator implements ItemGenerator {

   @Override
   public ItemCookedChicken generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCookedChicken( data, amount, nbt );
   }

   @Override
   public ItemCookedChicken generate( short data, byte amount ) {
       return new ItemCookedChicken( data, amount );
   }

}
