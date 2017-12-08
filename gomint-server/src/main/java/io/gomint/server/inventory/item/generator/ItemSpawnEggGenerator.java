package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSpawnEgg;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSpawnEggGenerator implements ItemGenerator {

   @Override
   public ItemSpawnEgg generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSpawnEgg( data, amount, nbt );
   }

   @Override
   public ItemSpawnEgg generate( short data, byte amount ) {
       return new ItemSpawnEgg( data, amount );
   }

}
