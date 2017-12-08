package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGlowstone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGlowstoneGenerator implements ItemGenerator {

   @Override
   public ItemGlowstone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGlowstone( data, amount, nbt );
   }

   @Override
   public ItemGlowstone generate( short data, byte amount ) {
       return new ItemGlowstone( data, amount );
   }

}
