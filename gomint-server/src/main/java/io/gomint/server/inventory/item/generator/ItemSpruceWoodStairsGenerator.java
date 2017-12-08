package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSpruceWoodStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSpruceWoodStairsGenerator implements ItemGenerator {

   @Override
   public ItemSpruceWoodStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSpruceWoodStairs( data, amount, nbt );
   }

   @Override
   public ItemSpruceWoodStairs generate( short data, byte amount ) {
       return new ItemSpruceWoodStairs( data, amount );
   }

}
