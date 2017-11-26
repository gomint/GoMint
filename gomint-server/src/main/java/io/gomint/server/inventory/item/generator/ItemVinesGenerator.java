package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemVines;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemVinesGenerator implements ItemGenerator {

   @Override
   public ItemVines generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemVines( data, amount, nbt );
   }

   @Override
   public ItemVines generate( short data, byte amount ) {
       return new ItemVines( data, amount );
   }

}
