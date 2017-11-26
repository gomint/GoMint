package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenDoorGenerator implements ItemGenerator {

   @Override
   public ItemWoodenDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenDoor( data, amount, nbt );
   }

   @Override
   public ItemWoodenDoor generate( short data, byte amount ) {
       return new ItemWoodenDoor( data, amount );
   }

}
