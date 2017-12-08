package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSandstoneStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSandstoneStairsGenerator implements ItemGenerator {

   @Override
   public ItemSandstoneStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSandstoneStairs( data, amount, nbt );
   }

   @Override
   public ItemSandstoneStairs generate( short data, byte amount ) {
       return new ItemSandstoneStairs( data, amount );
   }

}
