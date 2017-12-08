package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSpruceFenceGate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSpruceFenceGateGenerator implements ItemGenerator {

   @Override
   public ItemSpruceFenceGate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSpruceFenceGate( data, amount, nbt );
   }

   @Override
   public ItemSpruceFenceGate generate( short data, byte amount ) {
       return new ItemSpruceFenceGate( data, amount );
   }

}
