package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemReeds;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemReedsGenerator implements ItemGenerator {

   @Override
   public ItemReeds generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemReeds( data, amount, nbt );
   }

   @Override
   public ItemReeds generate( short data, byte amount ) {
       return new ItemReeds( data, amount );
   }

}
