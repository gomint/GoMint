package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronAxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronAxeGenerator implements ItemGenerator {

   @Override
   public ItemIronAxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronAxe( data, amount, nbt );
   }

   @Override
   public ItemIronAxe generate( short data, byte amount ) {
       return new ItemIronAxe( data, amount );
   }

}
