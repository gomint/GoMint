package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemHeavyWeightedPressurePlate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemHeavyWeightedPressurePlateGenerator implements ItemGenerator {

   @Override
   public ItemHeavyWeightedPressurePlate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemHeavyWeightedPressurePlate( data, amount, nbt );
   }

   @Override
   public ItemHeavyWeightedPressurePlate generate( short data, byte amount ) {
       return new ItemHeavyWeightedPressurePlate( data, amount );
   }

}
