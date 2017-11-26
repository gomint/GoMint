package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAcaciaFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAcaciaFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemAcaciaFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAcaciaFenceGate( data, amount, nbt );
   }

   @Override
   public ItemAcaciaFenceGate generate( short data, byte amount ) {
       return new ItemAcaciaFenceGate( data, amount );
   }

}
