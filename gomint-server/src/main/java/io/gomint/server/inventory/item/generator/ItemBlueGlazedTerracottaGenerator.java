package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlueGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlueGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemBlueGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlueGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemBlueGlazedTerracotta generate( short data, byte amount ) {
       return new ItemBlueGlazedTerracotta( data, amount );
   }

}
