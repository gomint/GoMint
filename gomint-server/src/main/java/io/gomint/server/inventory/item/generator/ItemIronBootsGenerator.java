package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronBoots;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronBootsGenerator implements ItemGenerator {

   @Override
   public ItemIronBoots generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronBoots( data, amount, nbt );
   }

   @Override
   public ItemIronBoots generate( short data, byte amount ) {
       return new ItemIronBoots( data, amount );
   }

}
