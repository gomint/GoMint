package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodPlanks;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodPlanksGenerator implements ItemGenerator {

   @Override
   public ItemWoodPlanks generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodPlanks( data, amount, nbt );
   }

   @Override
   public ItemWoodPlanks generate( short data, byte amount ) {
       return new ItemWoodPlanks( data, amount );
   }

}
