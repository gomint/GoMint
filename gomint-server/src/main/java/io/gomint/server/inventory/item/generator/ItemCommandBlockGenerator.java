package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCommandBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCommandBlockGenerator implements ItemGenerator {

   @Override
   public ItemCommandBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCommandBlock( data, amount, nbt );
   }

   @Override
   public ItemCommandBlock generate( short data, byte amount ) {
       return new ItemCommandBlock( data, amount );
   }

}
