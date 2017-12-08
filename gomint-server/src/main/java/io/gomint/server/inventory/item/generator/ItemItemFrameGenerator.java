package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemItemFrame;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemItemFrameGenerator implements ItemGenerator {

   @Override
   public ItemItemFrame generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemItemFrame( data, amount, nbt );
   }

   @Override
   public ItemItemFrame generate( short data, byte amount ) {
       return new ItemItemFrame( data, amount );
   }

}
