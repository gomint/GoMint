package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAcaciaWoodStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAcaciaWoodStairsGenerator implements ItemGenerator {

   @Override
   public ItemAcaciaWoodStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAcaciaWoodStairs( data, amount, nbt );
   }

   @Override
   public ItemAcaciaWoodStairs generate( short data, byte amount ) {
       return new ItemAcaciaWoodStairs( data, amount );
   }

}
