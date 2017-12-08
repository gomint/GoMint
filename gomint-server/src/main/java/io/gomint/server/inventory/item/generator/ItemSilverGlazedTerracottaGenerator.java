package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSilverGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSilverGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemSilverGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSilverGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemSilverGlazedTerracotta generate( short data, byte amount ) {
       return new ItemSilverGlazedTerracotta( data, amount );
   }

}
