package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSugarCane;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSugarCaneGenerator implements ItemGenerator {

   @Override
   public ItemSugarCane generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSugarCane( data, amount, nbt );
   }

   @Override
   public ItemSugarCane generate( short data, byte amount ) {
       return new ItemSugarCane( data, amount );
   }

}
