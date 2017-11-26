package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStoneShovel;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneShovelGenerator implements ItemGenerator {

   @Override
   public ItemStoneShovel generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStoneShovel( data, amount, nbt );
   }

   @Override
   public ItemStoneShovel generate( short data, byte amount ) {
       return new ItemStoneShovel( data, amount );
   }

}
