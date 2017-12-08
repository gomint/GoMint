package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSlimeBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSlimeBlockGenerator implements ItemGenerator {

   @Override
   public ItemSlimeBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSlimeBlock( data, amount, nbt );
   }

   @Override
   public ItemSlimeBlock generate( short data, byte amount ) {
       return new ItemSlimeBlock( data, amount );
   }

}
