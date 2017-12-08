package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronDoorGenerator implements ItemGenerator {

   @Override
   public ItemIronDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronDoor( data, amount, nbt );
   }

   @Override
   public ItemIronDoor generate( short data, byte amount ) {
       return new ItemIronDoor( data, amount );
   }

}
