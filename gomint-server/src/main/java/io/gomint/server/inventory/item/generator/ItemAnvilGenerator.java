package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAnvil;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAnvilGenerator implements ItemGenerator {

   @Override
   public ItemAnvil generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAnvil( data, amount, nbt );
   }

   @Override
   public ItemAnvil generate( short data, byte amount ) {
       return new ItemAnvil( data, amount );
   }

}
