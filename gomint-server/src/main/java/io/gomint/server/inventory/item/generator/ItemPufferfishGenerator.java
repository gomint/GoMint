package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPufferfish;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPufferfishGenerator implements ItemGenerator {

   @Override
   public ItemPufferfish generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPufferfish( data, amount, nbt );
   }

   @Override
   public ItemPufferfish generate( short data, byte amount ) {
       return new ItemPufferfish( data, amount );
   }

}
