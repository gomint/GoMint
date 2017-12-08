package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCookedMutton;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCookedMuttonGenerator implements ItemGenerator {

   @Override
   public ItemCookedMutton generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCookedMutton( data, amount, nbt );
   }

   @Override
   public ItemCookedMutton generate( short data, byte amount ) {
       return new ItemCookedMutton( data, amount );
   }

}
