package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRawPorkchop;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRawPorkchopGenerator implements ItemGenerator {

   @Override
   public ItemRawPorkchop generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRawPorkchop( data, amount, nbt );
   }

   @Override
   public ItemRawPorkchop generate( short data, byte amount ) {
       return new ItemRawPorkchop( data, amount );
   }

}
