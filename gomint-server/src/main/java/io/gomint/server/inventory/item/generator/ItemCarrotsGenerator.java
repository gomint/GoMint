package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCarrots;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCarrotsGenerator implements ItemGenerator {

   @Override
   public ItemCarrots generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCarrots( data, amount, nbt );
   }

   @Override
   public ItemCarrots generate( short data, byte amount ) {
       return new ItemCarrots( data, amount );
   }

}
