package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLightWeightedPressurePlate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLightWeightedPressurePlateGenerator implements ItemGenerator {

   @Override
   public ItemLightWeightedPressurePlate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLightWeightedPressurePlate( data, amount, nbt );
   }

   @Override
   public ItemLightWeightedPressurePlate generate( short data, byte amount ) {
       return new ItemLightWeightedPressurePlate( data, amount );
   }

}
