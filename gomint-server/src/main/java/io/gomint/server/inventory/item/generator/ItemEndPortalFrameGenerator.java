package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEndPortalFrame;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEndPortalFrameGenerator implements ItemGenerator {

   @Override
   public ItemEndPortalFrame generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEndPortalFrame( data, amount, nbt );
   }

   @Override
   public ItemEndPortalFrame generate( short data, byte amount ) {
       return new ItemEndPortalFrame( data, amount );
   }

}
