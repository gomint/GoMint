package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlackGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlackGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemBlackGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlackGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemBlackGlazedTerracotta generate( short data, byte amount ) {
       return new ItemBlackGlazedTerracotta( data, amount );
   }

}
