package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCookedBeef;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCookedBeefGenerator implements ItemGenerator {

   @Override
   public ItemCookedBeef generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCookedBeef( data, amount, nbt );
   }

   @Override
   public ItemCookedBeef generate( short data, byte amount ) {
       return new ItemCookedBeef( data, amount );
   }

}
