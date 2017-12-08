package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStoneAxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneAxeGenerator implements ItemGenerator {

   @Override
   public ItemStoneAxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStoneAxe( data, amount, nbt );
   }

   @Override
   public ItemStoneAxe generate( short data, byte amount ) {
       return new ItemStoneAxe( data, amount );
   }

}
