package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherrack;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherrackGenerator implements ItemGenerator {

   @Override
   public ItemNetherrack generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherrack( data, amount, nbt );
   }

   @Override
   public ItemNetherrack generate( short data, byte amount ) {
       return new ItemNetherrack( data, amount );
   }

}
