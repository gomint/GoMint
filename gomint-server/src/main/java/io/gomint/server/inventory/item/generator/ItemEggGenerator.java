package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEgg;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEggGenerator implements ItemGenerator {

   @Override
   public ItemEgg generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEgg( data, amount, nbt );
   }

   @Override
   public ItemEgg generate( short data, byte amount ) {
       return new ItemEgg( data, amount );
   }

}
