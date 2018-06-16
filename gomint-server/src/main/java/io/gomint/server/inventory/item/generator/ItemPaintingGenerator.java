package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPainting;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPaintingGenerator implements ItemGenerator {

   @Override
   public ItemPainting generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPainting( data, amount, nbt );
   }

   @Override
   public ItemPainting generate( short data, byte amount ) {
       return new ItemPainting( data, amount );
   }

}
