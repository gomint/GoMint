package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCookedPorkchop;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCookedPorkchopGenerator implements ItemGenerator {

   @Override
   public ItemCookedPorkchop generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCookedPorkchop( data, amount, nbt );
   }

   @Override
   public ItemCookedPorkchop generate( short data, byte amount ) {
       return new ItemCookedPorkchop( data, amount );
   }

}
