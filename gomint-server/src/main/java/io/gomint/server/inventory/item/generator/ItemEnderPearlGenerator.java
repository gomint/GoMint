package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEnderPearl;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEnderPearlGenerator implements ItemGenerator {

   @Override
   public ItemEnderPearl generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEnderPearl( data, amount, nbt );
   }

   @Override
   public ItemEnderPearl generate( short data, byte amount ) {
       return new ItemEnderPearl( data, amount );
   }

}
