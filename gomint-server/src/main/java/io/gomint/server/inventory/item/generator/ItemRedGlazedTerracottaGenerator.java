package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemRedGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemRedGlazedTerracotta generate( short data, byte amount ) {
       return new ItemRedGlazedTerracotta( data, amount );
   }

}
