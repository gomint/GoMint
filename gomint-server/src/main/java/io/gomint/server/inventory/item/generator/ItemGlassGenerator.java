package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGlass;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGlassGenerator implements ItemGenerator {

   @Override
   public ItemGlass generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGlass( data, amount, nbt );
   }

   @Override
   public ItemGlass generate( short data, byte amount ) {
       return new ItemGlass( data, amount );
   }

}
