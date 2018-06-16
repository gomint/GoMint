package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRawBeef;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRawBeefGenerator implements ItemGenerator {

   @Override
   public ItemRawBeef generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRawBeef( data, amount, nbt );
   }

   @Override
   public ItemRawBeef generate( short data, byte amount ) {
       return new ItemRawBeef( data, amount );
   }

}
