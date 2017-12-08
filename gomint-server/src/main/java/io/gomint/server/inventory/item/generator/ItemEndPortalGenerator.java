package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEndPortal;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEndPortalGenerator implements ItemGenerator {

   @Override
   public ItemEndPortal generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEndPortal( data, amount, nbt );
   }

   @Override
   public ItemEndPortal generate( short data, byte amount ) {
       return new ItemEndPortal( data, amount );
   }

}
