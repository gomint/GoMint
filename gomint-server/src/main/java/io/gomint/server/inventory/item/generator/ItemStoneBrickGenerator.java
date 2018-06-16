package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStoneBrick;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneBrickGenerator implements ItemGenerator {

   @Override
   public ItemStoneBrick generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStoneBrick( data, amount, nbt );
   }

   @Override
   public ItemStoneBrick generate( short data, byte amount ) {
       return new ItemStoneBrick( data, amount );
   }

}
