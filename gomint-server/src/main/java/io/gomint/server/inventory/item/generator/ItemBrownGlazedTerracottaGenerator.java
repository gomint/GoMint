package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBrownGlazedTerracotta;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBrownGlazedTerracottaGenerator implements ItemGenerator {

   @Override
   public ItemBrownGlazedTerracotta generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBrownGlazedTerracotta( data, amount, nbt );
   }

   @Override
   public ItemBrownGlazedTerracotta generate( short data, byte amount ) {
       return new ItemBrownGlazedTerracotta( data, amount );
   }

}
