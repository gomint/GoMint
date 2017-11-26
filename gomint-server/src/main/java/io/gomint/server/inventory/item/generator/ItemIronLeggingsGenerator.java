package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronLeggings;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronLeggingsGenerator implements ItemGenerator {

   @Override
   public ItemIronLeggings generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronLeggings( data, amount, nbt );
   }

   @Override
   public ItemIronLeggings generate( short data, byte amount ) {
       return new ItemIronLeggings( data, amount );
   }

}
