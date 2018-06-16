package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIce;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIceGenerator implements ItemGenerator {

   @Override
   public ItemIce generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIce( data, amount, nbt );
   }

   @Override
   public ItemIce generate( short data, byte amount ) {
       return new ItemIce( data, amount );
   }

}
