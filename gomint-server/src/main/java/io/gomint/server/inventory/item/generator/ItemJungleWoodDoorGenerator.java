package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemJungleWoodDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemJungleWoodDoorGenerator implements ItemGenerator {

   @Override
   public ItemJungleWoodDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemJungleWoodDoor( data, amount, nbt );
   }

   @Override
   public ItemJungleWoodDoor generate( short data, byte amount ) {
       return new ItemJungleWoodDoor( data, amount );
   }

}
