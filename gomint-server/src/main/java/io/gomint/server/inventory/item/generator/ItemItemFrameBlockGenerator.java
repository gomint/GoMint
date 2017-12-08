package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemItemFrameBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemItemFrameBlockGenerator implements ItemGenerator {

   @Override
   public ItemItemFrameBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemItemFrameBlock( data, amount, nbt );
   }

   @Override
   public ItemItemFrameBlock generate( short data, byte amount ) {
       return new ItemItemFrameBlock( data, amount );
   }

}
