package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDragonEgg;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDragonEggGenerator implements ItemGenerator {

   @Override
   public ItemDragonEgg generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDragonEgg( data, amount, nbt );
   }

   @Override
   public ItemDragonEgg generate( short data, byte amount ) {
       return new ItemDragonEgg( data, amount );
   }

}
