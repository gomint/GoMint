package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDirt;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDirtGenerator implements ItemGenerator {

   @Override
   public ItemDirt generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDirt( data, amount, nbt );
   }

   @Override
   public ItemDirt generate( short data, byte amount ) {
       return new ItemDirt( data, amount );
   }

}
