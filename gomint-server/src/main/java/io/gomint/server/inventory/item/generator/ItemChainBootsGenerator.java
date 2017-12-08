package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemChainBoots;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemChainBootsGenerator implements ItemGenerator {

   @Override
   public ItemChainBoots generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemChainBoots( data, amount, nbt );
   }

   @Override
   public ItemChainBoots generate( short data, byte amount ) {
       return new ItemChainBoots( data, amount );
   }

}
