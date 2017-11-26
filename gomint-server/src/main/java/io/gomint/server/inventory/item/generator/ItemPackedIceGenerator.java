package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPackedIce;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPackedIceGenerator implements ItemGenerator {

   @Override
   public ItemPackedIce generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPackedIce( data, amount, nbt );
   }

   @Override
   public ItemPackedIce generate( short data, byte amount ) {
       return new ItemPackedIce( data, amount );
   }

}
