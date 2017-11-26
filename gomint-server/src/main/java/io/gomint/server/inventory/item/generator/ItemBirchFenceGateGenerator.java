package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBirchFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBirchFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemBirchFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBirchFenceGate( data, amount, nbt );
   }

   @Override
   public ItemBirchFenceGate generate( short data, byte amount ) {
       return new ItemBirchFenceGate( data, amount );
   }

}
