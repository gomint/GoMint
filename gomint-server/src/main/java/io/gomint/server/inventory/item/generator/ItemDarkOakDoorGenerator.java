package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDarkOakDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDarkOakDoorGenerator implements ItemGenerator {

   @Override
   public ItemDarkOakDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDarkOakDoor( data, amount, nbt );
   }

   @Override
   public ItemDarkOakDoor generate( short data, byte amount ) {
       return new ItemDarkOakDoor( data, amount );
   }

}
