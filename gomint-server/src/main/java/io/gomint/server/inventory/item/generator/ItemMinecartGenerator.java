package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMinecart;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMinecartGenerator implements ItemGenerator {

   @Override
   public ItemMinecart generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMinecart( data, amount, nbt );
   }

   @Override
   public ItemMinecart generate( short data, byte amount ) {
       return new ItemMinecart( data, amount );
   }

}
