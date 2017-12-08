package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLimeGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLimeGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemLimeGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLimeGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemLimeGlazedTerracotta generate( short data, byte amount ) {
       return new ItemLimeGlazedTerracotta( data, amount );
   }

}
