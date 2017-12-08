package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRabbitFoot;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRabbitFootGenerator implements ItemGenerator {

   @Override
   public ItemRabbitFoot generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRabbitFoot( data, amount, nbt );
   }

   @Override
   public ItemRabbitFoot generate( short data, byte amount ) {
       return new ItemRabbitFoot( data, amount );
   }

}
