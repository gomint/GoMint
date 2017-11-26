package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronHoe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronHoeGenerator implements ItemGenerator {

   @Override
   public ItemIronHoe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronHoe( data, amount, nbt );
   }

   @Override
   public ItemIronHoe generate( short data, byte amount ) {
       return new ItemIronHoe( data, amount );
   }

}
