package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGlowstoneDust;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGlowstoneDustGenerator implements ItemGenerator {

   @Override
   public ItemGlowstoneDust generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGlowstoneDust( data, amount, nbt );
   }

   @Override
   public ItemGlowstoneDust generate( short data, byte amount ) {
       return new ItemGlowstoneDust( data, amount );
   }

}
