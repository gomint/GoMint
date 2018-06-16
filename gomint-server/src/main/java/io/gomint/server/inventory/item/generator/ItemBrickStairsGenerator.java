package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBrickStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBrickStairsGenerator implements ItemGenerator {

   @Override
   public ItemBrickStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBrickStairs( data, amount, nbt );
   }

   @Override
   public ItemBrickStairs generate( short data, byte amount ) {
       return new ItemBrickStairs( data, amount );
   }

}
