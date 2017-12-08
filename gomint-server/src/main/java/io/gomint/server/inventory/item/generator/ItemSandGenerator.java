package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSand;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSandGenerator implements ItemGenerator {

   @Override
   public ItemSand generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSand( data, amount, nbt );
   }

   @Override
   public ItemSand generate( short data, byte amount ) {
       return new ItemSand( data, amount );
   }

}
