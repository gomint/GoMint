package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLightBlueGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLightBlueGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemLightBlueGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLightBlueGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemLightBlueGlazedTerracotta generate( short data, byte amount ) {
       return new ItemLightBlueGlazedTerracotta( data, amount );
   }

}
