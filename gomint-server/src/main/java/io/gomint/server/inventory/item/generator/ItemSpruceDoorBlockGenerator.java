package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSpruceDoorBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSpruceDoorBlockGenerator implements ItemGenerator {

   @Override
   public ItemSpruceDoorBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSpruceDoorBlock( data, amount, nbt );
   }

   @Override
   public ItemSpruceDoorBlock generate( short data, byte amount ) {
       return new ItemSpruceDoorBlock( data, amount );
   }

}
