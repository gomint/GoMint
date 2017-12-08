package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAcaciaWoodDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAcaciaWoodDoorGenerator implements ItemGenerator {

   @Override
   public ItemAcaciaWoodDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAcaciaWoodDoor( data, amount, nbt );
   }

   @Override
   public ItemAcaciaWoodDoor generate( short data, byte amount ) {
       return new ItemAcaciaWoodDoor( data, amount );
   }

}
