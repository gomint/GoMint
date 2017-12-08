package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRawSalmon;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRawSalmonGenerator implements ItemGenerator {

   @Override
   public ItemRawSalmon generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRawSalmon( data, amount, nbt );
   }

   @Override
   public ItemRawSalmon generate( short data, byte amount ) {
       return new ItemRawSalmon( data, amount );
   }

}
