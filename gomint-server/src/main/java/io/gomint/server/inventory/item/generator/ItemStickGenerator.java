package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStick;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStickGenerator implements ItemGenerator {

   @Override
   public ItemStick generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStick( data, amount, nbt );
   }

   @Override
   public ItemStick generate( short data, byte amount ) {
       return new ItemStick( data, amount );
   }

}
