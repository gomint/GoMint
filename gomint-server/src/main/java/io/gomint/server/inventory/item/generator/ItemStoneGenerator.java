package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneGenerator implements ItemGenerator {

   @Override
   public ItemStone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStone( data, amount, nbt );
   }

   @Override
   public ItemStone generate( short data, byte amount ) {
       return new ItemStone( data, amount );
   }

}
