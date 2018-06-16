package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDarkOakWoodStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDarkOakWoodStairsGenerator implements ItemGenerator {

   @Override
   public ItemDarkOakWoodStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDarkOakWoodStairs( data, amount, nbt );
   }

   @Override
   public ItemDarkOakWoodStairs generate( short data, byte amount ) {
       return new ItemDarkOakWoodStairs( data, amount );
   }

}
