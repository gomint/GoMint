package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenShovel;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenShovelGenerator implements ItemGenerator {

   @Override
   public ItemGoldenShovel generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenShovel( data, amount, nbt );
   }

   @Override
   public ItemGoldenShovel generate( short data, byte amount ) {
       return new ItemGoldenShovel( data, amount );
   }

}
