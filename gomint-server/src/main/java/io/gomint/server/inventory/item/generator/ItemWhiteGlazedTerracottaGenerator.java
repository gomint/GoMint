package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWhiteGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWhiteGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemWhiteGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWhiteGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemWhiteGlazedTerracotta generate( short data, byte amount ) {
       return new ItemWhiteGlazedTerracotta( data, amount );
   }

}
