package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPoweredRail;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPoweredRailGenerator implements ItemGenerator {

   @Override
   public ItemPoweredRail generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPoweredRail( data, amount, nbt );
   }

   @Override
   public ItemPoweredRail generate( short data, byte amount ) {
       return new ItemPoweredRail( data, amount );
   }

}
