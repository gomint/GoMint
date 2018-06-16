package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneLampInactive;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneLampInactiveGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneLampInactive generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneLampInactive( data, amount, nbt );
   }

   @Override
   public ItemRedstoneLampInactive generate( short data, byte amount ) {
       return new ItemRedstoneLampInactive( data, amount );
   }

}
