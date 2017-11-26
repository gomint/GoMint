package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNoteBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNoteBlockGenerator implements ItemGenerator {

   @Override
   public ItemNoteBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNoteBlock( data, amount, nbt );
   }

   @Override
   public ItemNoteBlock generate( short data, byte amount ) {
       return new ItemNoteBlock( data, amount );
   }

}
