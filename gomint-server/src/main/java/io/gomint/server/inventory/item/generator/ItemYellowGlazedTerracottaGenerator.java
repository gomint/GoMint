package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemYellowGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemYellowGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemYellowGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemYellowGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemYellowGlazedTerracotta generate( short data, byte amount ) {
       return new ItemYellowGlazedTerracotta( data, amount );
   }

}
