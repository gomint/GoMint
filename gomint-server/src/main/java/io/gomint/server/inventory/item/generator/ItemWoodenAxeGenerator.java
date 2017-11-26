package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenAxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenAxeGenerator implements ItemGenerator {

   @Override
   public ItemWoodenAxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenAxe( data, amount, nbt );
   }

   @Override
   public ItemWoodenAxe generate( short data, byte amount ) {
       return new ItemWoodenAxe( data, amount );
   }

}
