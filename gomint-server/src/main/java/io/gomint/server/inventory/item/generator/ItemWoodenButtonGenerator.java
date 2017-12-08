package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenButton;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenButtonGenerator implements ItemGenerator {

   @Override
   public ItemWoodenButton generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenButton( data, amount, nbt );
   }

   @Override
   public ItemWoodenButton generate( short data, byte amount ) {
       return new ItemWoodenButton( data, amount );
   }

}
