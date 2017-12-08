package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDarkOakWoodFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDarkOakWoodFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemDarkOakWoodFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDarkOakWoodFenceGate( data, amount, nbt );
   }

   @Override
   public ItemDarkOakWoodFenceGate generate( short data, byte amount ) {
       return new ItemDarkOakWoodFenceGate( data, amount );
   }

}
