package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBread;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBreadGenerator implements ItemGenerator {

   @Override
   public ItemBread generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBread( data, amount, nbt );
   }

   @Override
   public ItemBread generate( short data, byte amount ) {
       return new ItemBread( data, amount );
   }

}
