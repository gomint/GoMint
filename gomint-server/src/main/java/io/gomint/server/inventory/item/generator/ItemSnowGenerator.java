package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSnow;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSnowGenerator implements ItemGenerator {

   @Override
   public ItemSnow generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSnow( data, amount, nbt );
   }

   @Override
   public ItemSnow generate( short data, byte amount ) {
       return new ItemSnow( data, amount );
   }

}
