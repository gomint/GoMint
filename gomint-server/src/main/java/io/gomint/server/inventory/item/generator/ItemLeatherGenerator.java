package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLeather;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeatherGenerator implements ItemGenerator {

   @Override
   public ItemLeather generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLeather( data, amount, nbt );
   }

   @Override
   public ItemLeather generate( short data, byte amount ) {
       return new ItemLeather( data, amount );
   }

}
