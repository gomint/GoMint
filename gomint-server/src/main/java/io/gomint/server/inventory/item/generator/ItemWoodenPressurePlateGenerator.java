package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenPressurePlate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenPressurePlateGenerator implements ItemGenerator {

   @Override
   public ItemWoodenPressurePlate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenPressurePlate( data, amount, nbt );
   }

   @Override
   public ItemWoodenPressurePlate generate( short data, byte amount ) {
       return new ItemWoodenPressurePlate( data, amount );
   }

}
