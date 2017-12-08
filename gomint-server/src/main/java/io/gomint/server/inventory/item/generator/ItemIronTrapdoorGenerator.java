package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronTrapdoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronTrapdoorGenerator implements ItemGenerator {

   @Override
   public ItemIronTrapdoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronTrapdoor( data, amount, nbt );
   }

   @Override
   public ItemIronTrapdoor generate( short data, byte amount ) {
       return new ItemIronTrapdoor( data, amount );
   }

}
