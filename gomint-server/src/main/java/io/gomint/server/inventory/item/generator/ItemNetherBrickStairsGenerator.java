package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherBrickStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherBrickStairsGenerator implements ItemGenerator {

   @Override
   public ItemNetherBrickStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherBrickStairs( data, amount, nbt );
   }

   @Override
   public ItemNetherBrickStairs generate( short data, byte amount ) {
       return new ItemNetherBrickStairs( data, amount );
   }

}
