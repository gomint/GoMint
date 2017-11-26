package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFenceGate( data, amount, nbt );
   }

   @Override
   public ItemFenceGate generate( short data, byte amount ) {
       return new ItemFenceGate( data, amount );
   }

}
