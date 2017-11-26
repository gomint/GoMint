package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAir;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAirGenerator implements ItemGenerator {

   @Override
   public ItemAir generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAir( data, amount, nbt );
   }

   @Override
   public ItemAir generate( short data, byte amount ) {
       return new ItemAir( data, amount );
   }

}
