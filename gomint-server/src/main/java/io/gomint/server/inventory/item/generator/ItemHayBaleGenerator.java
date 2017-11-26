package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemHayBale;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemHayBaleGenerator implements ItemGenerator {

   @Override
   public ItemHayBale generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemHayBale( data, amount, nbt );
   }

   @Override
   public ItemHayBale generate( short data, byte amount ) {
       return new ItemHayBale( data, amount );
   }

}
