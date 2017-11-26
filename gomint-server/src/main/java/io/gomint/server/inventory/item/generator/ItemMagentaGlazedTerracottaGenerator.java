package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMagentaGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMagentaGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemMagentaGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMagentaGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemMagentaGlazedTerracotta generate( short data, byte amount ) {
       return new ItemMagentaGlazedTerracotta( data, amount );
   }

}
