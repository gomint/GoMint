package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMap;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMapGenerator implements ItemGenerator {

   @Override
   public ItemMap generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMap( data, amount, nbt );
   }

   @Override
   public ItemMap generate( short data, byte amount ) {
       return new ItemMap( data, amount );
   }

}
