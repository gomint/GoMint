package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemJungleFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemJungleFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemJungleFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemJungleFenceGate( data, amount, nbt );
   }

   @Override
   public ItemJungleFenceGate generate( short data, byte amount ) {
       return new ItemJungleFenceGate( data, amount );
   }

}
