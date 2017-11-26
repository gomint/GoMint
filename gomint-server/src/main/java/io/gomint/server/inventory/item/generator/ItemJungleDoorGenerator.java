package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemJungleDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemJungleDoorGenerator implements ItemGenerator {

   @Override
   public ItemJungleDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemJungleDoor( data, amount, nbt );
   }

   @Override
   public ItemJungleDoor generate( short data, byte amount ) {
       return new ItemJungleDoor( data, amount );
   }

}
