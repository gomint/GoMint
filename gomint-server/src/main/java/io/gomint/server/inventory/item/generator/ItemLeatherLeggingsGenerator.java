package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLeatherLeggings;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeatherLeggingsGenerator implements ItemGenerator {

   @Override
   public ItemLeatherLeggings generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLeatherLeggings( data, amount, nbt );
   }

   @Override
   public ItemLeatherLeggings generate( short data, byte amount ) {
       return new ItemLeatherLeggings( data, amount );
   }

}
