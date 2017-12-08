package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemJungleWoodFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemJungleWoodFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemJungleWoodFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemJungleWoodFenceGate( data, amount, nbt );
   }

   @Override
   public ItemJungleWoodFenceGate generate( short data, byte amount ) {
       return new ItemJungleWoodFenceGate( data, amount );
   }

}
