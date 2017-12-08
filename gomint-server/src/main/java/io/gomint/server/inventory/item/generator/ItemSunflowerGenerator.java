package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSunflower;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSunflowerGenerator implements ItemGenerator {

   @Override
   public ItemSunflower generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSunflower( data, amount, nbt );
   }

   @Override
   public ItemSunflower generate( short data, byte amount ) {
       return new ItemSunflower( data, amount );
   }

}
