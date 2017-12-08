package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRabbitStew;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRabbitStewGenerator implements ItemGenerator {

   @Override
   public ItemRabbitStew generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRabbitStew( data, amount, nbt );
   }

   @Override
   public ItemRabbitStew generate( short data, byte amount ) {
       return new ItemRabbitStew( data, amount );
   }

}
