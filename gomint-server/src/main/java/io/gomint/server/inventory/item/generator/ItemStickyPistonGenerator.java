package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStickyPiston;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStickyPistonGenerator implements ItemGenerator {

   @Override
   public ItemStickyPiston generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStickyPiston( data, amount, nbt );
   }

   @Override
   public ItemStickyPiston generate( short data, byte amount ) {
       return new ItemStickyPiston( data, amount );
   }

}
