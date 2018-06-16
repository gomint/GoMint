package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenHoe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenHoeGenerator implements ItemGenerator {

   @Override
   public ItemGoldenHoe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenHoe( data, amount, nbt );
   }

   @Override
   public ItemGoldenHoe generate( short data, byte amount ) {
       return new ItemGoldenHoe( data, amount );
   }

}
