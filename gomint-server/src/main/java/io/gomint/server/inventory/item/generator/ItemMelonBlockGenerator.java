package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMelonBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMelonBlockGenerator implements ItemGenerator {

   @Override
   public ItemMelonBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMelonBlock( data, amount, nbt );
   }

   @Override
   public ItemMelonBlock generate( short data, byte amount ) {
       return new ItemMelonBlock( data, amount );
   }

}
