package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGrayGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGrayGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemGrayGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGrayGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemGrayGlazedTerracotta generate( short data, byte amount ) {
       return new ItemGrayGlazedTerracotta( data, amount );
   }

}
