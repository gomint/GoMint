package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSpruceDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSpruceDoorGenerator implements ItemGenerator {

   @Override
   public ItemSpruceDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSpruceDoor( data, amount, nbt );
   }

   @Override
   public ItemSpruceDoor generate( short data, byte amount ) {
       return new ItemSpruceDoor( data, amount );
   }

}
