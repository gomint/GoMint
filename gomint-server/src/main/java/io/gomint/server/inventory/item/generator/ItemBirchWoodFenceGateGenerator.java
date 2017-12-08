package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBirchWoodFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBirchWoodFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemBirchWoodFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBirchWoodFenceGate( data, amount, nbt );
   }

   @Override
   public ItemBirchWoodFenceGate generate( short data, byte amount ) {
       return new ItemBirchWoodFenceGate( data, amount );
   }

}
