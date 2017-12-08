package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMonsterEgg;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMonsterEggGenerator implements ItemGenerator {

   @Override
   public ItemMonsterEgg generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMonsterEgg( data, amount, nbt );
   }

   @Override
   public ItemMonsterEgg generate( short data, byte amount ) {
       return new ItemMonsterEgg( data, amount );
   }

}
