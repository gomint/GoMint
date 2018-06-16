package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMycelium;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMyceliumGenerator implements ItemGenerator {

   @Override
   public ItemMycelium generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMycelium( data, amount, nbt );
   }

   @Override
   public ItemMycelium generate( short data, byte amount ) {
       return new ItemMycelium( data, amount );
   }

}
