package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemJungleWoodStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemJungleWoodStairsGenerator implements ItemGenerator {

   @Override
   public ItemJungleWoodStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemJungleWoodStairs( data, amount, nbt );
   }

   @Override
   public ItemJungleWoodStairs generate( short data, byte amount ) {
       return new ItemJungleWoodStairs( data, amount );
   }

}
