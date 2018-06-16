package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenCarrot;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenCarrotGenerator implements ItemGenerator {

   @Override
   public ItemGoldenCarrot generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenCarrot( data, amount, nbt );
   }

   @Override
   public ItemGoldenCarrot generate( short data, byte amount ) {
       return new ItemGoldenCarrot( data, amount );
   }

}
