package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStonePressurePlate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStonePressurePlateGenerator implements ItemGenerator {

   @Override
   public ItemStonePressurePlate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStonePressurePlate( data, amount, nbt );
   }

   @Override
   public ItemStonePressurePlate generate( short data, byte amount ) {
       return new ItemStonePressurePlate( data, amount );
   }

}
