package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGlassBottle;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGlassBottleGenerator implements ItemGenerator {

   @Override
   public ItemGlassBottle generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGlassBottle( data, amount, nbt );
   }

   @Override
   public ItemGlassBottle generate( short data, byte amount ) {
       return new ItemGlassBottle( data, amount );
   }

}
