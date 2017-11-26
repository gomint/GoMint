package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronBars;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronBarsGenerator implements ItemGenerator {

   @Override
   public ItemIronBars generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronBars( data, amount, nbt );
   }

   @Override
   public ItemIronBars generate( short data, byte amount ) {
       return new ItemIronBars( data, amount );
   }

}
