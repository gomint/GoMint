package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDoubleStoneSlab;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDoubleStoneSlabGenerator implements ItemGenerator {

   @Override
   public ItemDoubleStoneSlab generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDoubleStoneSlab( data, amount, nbt );
   }

   @Override
   public ItemDoubleStoneSlab generate( short data, byte amount ) {
       return new ItemDoubleStoneSlab( data, amount );
   }

}
