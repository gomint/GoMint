package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBirchWoodDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBirchWoodDoorGenerator implements ItemGenerator {

   @Override
   public ItemBirchWoodDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBirchWoodDoor( data, amount, nbt );
   }

   @Override
   public ItemBirchWoodDoor generate( short data, byte amount ) {
       return new ItemBirchWoodDoor( data, amount );
   }

}
