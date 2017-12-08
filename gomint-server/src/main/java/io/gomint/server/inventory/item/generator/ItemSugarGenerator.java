package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSugar;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSugarGenerator implements ItemGenerator {

   @Override
   public ItemSugar generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSugar( data, amount, nbt );
   }

   @Override
   public ItemSugar generate( short data, byte amount ) {
       return new ItemSugar( data, amount );
   }

}
