package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFilledMap;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFilledMapGenerator implements ItemGenerator {

   @Override
   public ItemFilledMap generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFilledMap( data, amount, nbt );
   }

   @Override
   public ItemFilledMap generate( short data, byte amount ) {
       return new ItemFilledMap( data, amount );
   }

}
