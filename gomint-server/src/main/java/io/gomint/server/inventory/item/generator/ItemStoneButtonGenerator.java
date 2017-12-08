package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStoneButton;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneButtonGenerator implements ItemGenerator {

   @Override
   public ItemStoneButton generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStoneButton( data, amount, nbt );
   }

   @Override
   public ItemStoneButton generate( short data, byte amount ) {
       return new ItemStoneButton( data, amount );
   }

}
