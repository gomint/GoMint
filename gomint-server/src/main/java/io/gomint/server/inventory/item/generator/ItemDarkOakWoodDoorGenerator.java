package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDarkOakWoodDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDarkOakWoodDoorGenerator implements ItemGenerator {

   @Override
   public ItemDarkOakWoodDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDarkOakWoodDoor( data, amount, nbt );
   }

   @Override
   public ItemDarkOakWoodDoor generate( short data, byte amount ) {
       return new ItemDarkOakWoodDoor( data, amount );
   }

}
