package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemOakWoodStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemOakWoodStairsGenerator implements ItemGenerator {

   @Override
   public ItemOakWoodStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemOakWoodStairs( data, amount, nbt );
   }

   @Override
   public ItemOakWoodStairs generate( short data, byte amount ) {
       return new ItemOakWoodStairs( data, amount );
   }

}
