package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBirchWoodStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBirchWoodStairsGenerator implements ItemGenerator {

   @Override
   public ItemBirchWoodStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBirchWoodStairs( data, amount, nbt );
   }

   @Override
   public ItemBirchWoodStairs generate( short data, byte amount ) {
       return new ItemBirchWoodStairs( data, amount );
   }

}
