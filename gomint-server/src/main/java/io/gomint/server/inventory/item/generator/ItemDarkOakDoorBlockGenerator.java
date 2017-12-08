package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDarkOakDoorBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDarkOakDoorBlockGenerator implements ItemGenerator {

   @Override
   public ItemDarkOakDoorBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDarkOakDoorBlock( data, amount, nbt );
   }

   @Override
   public ItemDarkOakDoorBlock generate( short data, byte amount ) {
       return new ItemDarkOakDoorBlock( data, amount );
   }

}
