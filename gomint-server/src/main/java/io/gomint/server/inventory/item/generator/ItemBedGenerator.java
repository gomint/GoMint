package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBed;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBedGenerator implements ItemGenerator {

   @Override
   public ItemBed generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBed( data, amount, nbt );
   }

   @Override
   public ItemBed generate( short data, byte amount ) {
       return new ItemBed( data, amount );
   }

}
