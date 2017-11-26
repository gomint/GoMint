package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGreenGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGreenGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemGreenGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGreenGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemGreenGlazedTerracotta generate( short data, byte amount ) {
       return new ItemGreenGlazedTerracotta( data, amount );
   }

}
