package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPinkGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPinkGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemPinkGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPinkGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemPinkGlazedTerracotta generate( short data, byte amount ) {
       return new ItemPinkGlazedTerracotta( data, amount );
   }

}
