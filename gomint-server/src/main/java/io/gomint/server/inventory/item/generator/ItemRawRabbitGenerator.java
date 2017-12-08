package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRawRabbit;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRawRabbitGenerator implements ItemGenerator {

   @Override
   public ItemRawRabbit generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRawRabbit( data, amount, nbt );
   }

   @Override
   public ItemRawRabbit generate( short data, byte amount ) {
       return new ItemRawRabbit( data, amount );
   }

}
