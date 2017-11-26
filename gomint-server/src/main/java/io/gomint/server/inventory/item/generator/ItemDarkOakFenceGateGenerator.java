package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDarkOakFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDarkOakFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemDarkOakFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDarkOakFenceGate( data, amount, nbt );
   }

   @Override
   public ItemDarkOakFenceGate generate( short data, byte amount ) {
       return new ItemDarkOakFenceGate( data, amount );
   }

}
