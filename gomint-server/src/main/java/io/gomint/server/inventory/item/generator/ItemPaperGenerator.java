package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPaper;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPaperGenerator implements ItemGenerator {

   @Override
   public ItemPaper generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPaper( data, amount, nbt );
   }

   @Override
   public ItemPaper generate( short data, byte amount ) {
       return new ItemPaper( data, amount );
   }

}
