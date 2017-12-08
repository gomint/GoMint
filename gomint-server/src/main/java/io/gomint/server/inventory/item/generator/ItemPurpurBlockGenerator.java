package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPurpurBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPurpurBlockGenerator implements ItemGenerator {

   @Override
   public ItemPurpurBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPurpurBlock( data, amount, nbt );
   }

   @Override
   public ItemPurpurBlock generate( short data, byte amount ) {
       return new ItemPurpurBlock( data, amount );
   }

}
