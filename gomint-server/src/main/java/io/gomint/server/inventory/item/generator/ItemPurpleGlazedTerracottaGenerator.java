package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPurpleGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPurpleGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemPurpleGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPurpleGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemPurpleGlazedTerracotta generate( short data, byte amount ) {
       return new ItemPurpleGlazedTerracotta( data, amount );
   }

}
