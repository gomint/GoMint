package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEndStone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEndStoneGenerator implements ItemGenerator {

   @Override
   public ItemEndStone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEndStone( data, amount, nbt );
   }

   @Override
   public ItemEndStone generate( short data, byte amount ) {
       return new ItemEndStone( data, amount );
   }

}
