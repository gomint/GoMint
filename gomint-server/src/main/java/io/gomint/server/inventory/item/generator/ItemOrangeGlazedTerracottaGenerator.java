package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemOrangeGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemOrangeGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemOrangeGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemOrangeGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemOrangeGlazedTerracotta generate( short data, byte amount ) {
       return new ItemOrangeGlazedTerracotta( data, amount );
   }

}
