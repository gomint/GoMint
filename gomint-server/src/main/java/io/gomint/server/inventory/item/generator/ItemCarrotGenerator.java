package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCarrot;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCarrotGenerator implements ItemGenerator {

   @Override
   public ItemCarrot generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCarrot( data, amount, nbt );
   }

   @Override
   public ItemCarrot generate( short data, byte amount ) {
       return new ItemCarrot( data, amount );
   }

}
