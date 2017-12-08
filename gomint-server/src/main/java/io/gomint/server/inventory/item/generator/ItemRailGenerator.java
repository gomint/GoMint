package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRail;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRailGenerator implements ItemGenerator {

   @Override
   public ItemRail generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRail( data, amount, nbt );
   }

   @Override
   public ItemRail generate( short data, byte amount ) {
       return new ItemRail( data, amount );
   }

}
