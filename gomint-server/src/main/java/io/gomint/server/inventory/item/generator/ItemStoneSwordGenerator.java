package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStoneSword;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneSwordGenerator implements ItemGenerator {

   @Override
   public ItemStoneSword generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStoneSword( data, amount, nbt );
   }

   @Override
   public ItemStoneSword generate( short data, byte amount ) {
       return new ItemStoneSword( data, amount );
   }

}
