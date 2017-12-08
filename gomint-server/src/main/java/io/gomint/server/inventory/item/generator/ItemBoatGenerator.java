package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBoat;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBoatGenerator implements ItemGenerator {

   @Override
   public ItemBoat generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBoat( data, amount, nbt );
   }

   @Override
   public ItemBoat generate( short data, byte amount ) {
       return new ItemBoat( data, amount );
   }

}
