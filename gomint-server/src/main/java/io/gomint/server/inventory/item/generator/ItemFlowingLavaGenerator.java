package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFlowingLava;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFlowingLavaGenerator implements ItemGenerator {

   @Override
   public ItemFlowingLava generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFlowingLava( data, amount, nbt );
   }

   @Override
   public ItemFlowingLava generate( short data, byte amount ) {
       return new ItemFlowingLava( data, amount );
   }

}
