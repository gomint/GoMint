package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemString;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStringGenerator implements ItemGenerator {

   @Override
   public ItemString generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemString( data, amount, nbt );
   }

   @Override
   public ItemString generate( short data, byte amount ) {
       return new ItemString( data, amount );
   }

}
