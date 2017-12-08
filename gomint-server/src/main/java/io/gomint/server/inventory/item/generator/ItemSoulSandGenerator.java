package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSoulSand;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSoulSandGenerator implements ItemGenerator {

   @Override
   public ItemSoulSand generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSoulSand( data, amount, nbt );
   }

   @Override
   public ItemSoulSand generate( short data, byte amount ) {
       return new ItemSoulSand( data, amount );
   }

}
