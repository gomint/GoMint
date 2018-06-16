package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCyanGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCyanGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemCyanGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCyanGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemCyanGlazedTerracotta generate( short data, byte amount ) {
       return new ItemCyanGlazedTerracotta( data, amount );
   }

}
