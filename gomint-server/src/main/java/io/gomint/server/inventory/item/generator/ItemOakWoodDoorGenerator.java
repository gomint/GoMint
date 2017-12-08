package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemOakWoodDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemOakWoodDoorGenerator implements ItemGenerator {

   @Override
   public ItemOakWoodDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemOakWoodDoor( data, amount, nbt );
   }

   @Override
   public ItemOakWoodDoor generate( short data, byte amount ) {
       return new ItemOakWoodDoor( data, amount );
   }

}
