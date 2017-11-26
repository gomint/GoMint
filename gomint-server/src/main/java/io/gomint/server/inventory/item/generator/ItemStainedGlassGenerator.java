package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStainedGlass;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStainedGlassGenerator implements ItemGenerator {

   @Override
   public ItemStainedGlass generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStainedGlass( data, amount, nbt );
   }

   @Override
   public ItemStainedGlass generate( short data, byte amount ) {
       return new ItemStainedGlass( data, amount );
   }

}
