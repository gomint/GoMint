package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemChainLeggings;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemChainLeggingsGenerator implements ItemGenerator {

   @Override
   public ItemChainLeggings generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemChainLeggings( data, amount, nbt );
   }

   @Override
   public ItemChainLeggings generate( short data, byte amount ) {
       return new ItemChainLeggings( data, amount );
   }

}
