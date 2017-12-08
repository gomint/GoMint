package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronShovel;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronShovelGenerator implements ItemGenerator {

   @Override
   public ItemIronShovel generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronShovel( data, amount, nbt );
   }

   @Override
   public ItemIronShovel generate( short data, byte amount ) {
       return new ItemIronShovel( data, amount );
   }

}
