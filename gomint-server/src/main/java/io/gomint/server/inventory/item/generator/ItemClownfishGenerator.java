package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemClownfish;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemClownfishGenerator implements ItemGenerator {

   @Override
   public ItemClownfish generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemClownfish( data, amount, nbt );
   }

   @Override
   public ItemClownfish generate( short data, byte amount ) {
       return new ItemClownfish( data, amount );
   }

}
