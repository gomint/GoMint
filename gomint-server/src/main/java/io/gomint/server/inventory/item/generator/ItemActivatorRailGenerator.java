package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemActivatorRail;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemActivatorRailGenerator implements ItemGenerator {

   @Override
   public ItemActivatorRail generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemActivatorRail( data, amount, nbt );
   }

   @Override
   public ItemActivatorRail generate( short data, byte amount ) {
       return new ItemActivatorRail( data, amount );
   }

}
