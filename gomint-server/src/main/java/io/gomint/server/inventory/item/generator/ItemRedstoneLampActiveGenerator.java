package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneLampActive;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneLampActiveGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneLampActive generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneLampActive( data, amount, nbt );
   }

   @Override
   public ItemRedstoneLampActive generate( short data, byte amount ) {
       return new ItemRedstoneLampActive( data, amount );
   }

}
