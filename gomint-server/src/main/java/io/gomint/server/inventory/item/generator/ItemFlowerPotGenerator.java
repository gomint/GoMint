package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFlowerPot;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFlowerPotGenerator implements ItemGenerator {

   @Override
   public ItemFlowerPot generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFlowerPot( data, amount, nbt );
   }

   @Override
   public ItemFlowerPot generate( short data, byte amount ) {
       return new ItemFlowerPot( data, amount );
   }

}
