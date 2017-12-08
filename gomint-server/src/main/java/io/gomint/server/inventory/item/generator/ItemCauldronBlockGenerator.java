package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCauldronBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCauldronBlockGenerator implements ItemGenerator {

   @Override
   public ItemCauldronBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCauldronBlock( data, amount, nbt );
   }

   @Override
   public ItemCauldronBlock generate( short data, byte amount ) {
       return new ItemCauldronBlock( data, amount );
   }

}
