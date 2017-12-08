package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedSandstoneStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedSandstoneStairsGenerator implements ItemGenerator {

   @Override
   public ItemRedSandstoneStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedSandstoneStairs( data, amount, nbt );
   }

   @Override
   public ItemRedSandstoneStairs generate( short data, byte amount ) {
       return new ItemRedSandstoneStairs( data, amount );
   }

}
