package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPortal;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPortalGenerator implements ItemGenerator {

   @Override
   public ItemPortal generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPortal( data, amount, nbt );
   }

   @Override
   public ItemPortal generate( short data, byte amount ) {
       return new ItemPortal( data, amount );
   }

}
