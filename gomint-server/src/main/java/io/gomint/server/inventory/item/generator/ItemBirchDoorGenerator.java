package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBirchDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBirchDoorGenerator implements ItemGenerator {

   @Override
   public ItemBirchDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBirchDoor( data, amount, nbt );
   }

   @Override
   public ItemBirchDoor generate( short data, byte amount ) {
       return new ItemBirchDoor( data, amount );
   }

}
