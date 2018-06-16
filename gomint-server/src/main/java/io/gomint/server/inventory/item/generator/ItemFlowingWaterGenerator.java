package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFlowingWater;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFlowingWaterGenerator implements ItemGenerator {

   @Override
   public ItemFlowingWater generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFlowingWater( data, amount, nbt );
   }

   @Override
   public ItemFlowingWater generate( short data, byte amount ) {
       return new ItemFlowingWater( data, amount );
   }

}
