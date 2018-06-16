package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAcaciaWoodFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAcaciaWoodFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemAcaciaWoodFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAcaciaWoodFenceGate( data, amount, nbt );
   }

   @Override
   public ItemAcaciaWoodFenceGate generate( short data, byte amount ) {
       return new ItemAcaciaWoodFenceGate( data, amount );
   }

}
