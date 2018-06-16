package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSkull;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSkullGenerator implements ItemGenerator {

   @Override
   public ItemSkull generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSkull( data, amount, nbt );
   }

   @Override
   public ItemSkull generate( short data, byte amount ) {
       return new ItemSkull( data, amount );
   }

}
