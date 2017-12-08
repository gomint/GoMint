package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStoneSlab;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneSlabGenerator implements ItemGenerator {

   @Override
   public ItemStoneSlab generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStoneSlab( data, amount, nbt );
   }

   @Override
   public ItemStoneSlab generate( short data, byte amount ) {
       return new ItemStoneSlab( data, amount );
   }

}
