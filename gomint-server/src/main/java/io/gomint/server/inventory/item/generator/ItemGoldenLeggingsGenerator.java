package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenLeggings;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenLeggingsGenerator implements ItemGenerator {

   @Override
   public ItemGoldenLeggings generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenLeggings( data, amount, nbt );
   }

   @Override
   public ItemGoldenLeggings generate( short data, byte amount ) {
       return new ItemGoldenLeggings( data, amount );
   }

}
