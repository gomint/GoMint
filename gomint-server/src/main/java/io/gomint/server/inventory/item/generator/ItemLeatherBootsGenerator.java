package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLeatherBoots;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeatherBootsGenerator implements ItemGenerator {

   @Override
   public ItemLeatherBoots generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLeatherBoots( data, amount, nbt );
   }

   @Override
   public ItemLeatherBoots generate( short data, byte amount ) {
       return new ItemLeatherBoots( data, amount );
   }

}
