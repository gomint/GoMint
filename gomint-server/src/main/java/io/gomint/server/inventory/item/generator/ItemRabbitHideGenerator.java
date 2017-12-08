package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRabbitHide;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRabbitHideGenerator implements ItemGenerator {

   @Override
   public ItemRabbitHide generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRabbitHide( data, amount, nbt );
   }

   @Override
   public ItemRabbitHide generate( short data, byte amount ) {
       return new ItemRabbitHide( data, amount );
   }

}
