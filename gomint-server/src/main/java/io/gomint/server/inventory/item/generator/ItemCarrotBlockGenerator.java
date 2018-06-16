package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCarrotBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCarrotBlockGenerator implements ItemGenerator {

   @Override
   public ItemCarrotBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCarrotBlock( data, amount, nbt );
   }

   @Override
   public ItemCarrotBlock generate( short data, byte amount ) {
       return new ItemCarrotBlock( data, amount );
   }

}
