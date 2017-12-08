package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemQuartzStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemQuartzStairsGenerator implements ItemGenerator {

   @Override
   public ItemQuartzStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemQuartzStairs( data, amount, nbt );
   }

   @Override
   public ItemQuartzStairs generate( short data, byte amount ) {
       return new ItemQuartzStairs( data, amount );
   }

}
