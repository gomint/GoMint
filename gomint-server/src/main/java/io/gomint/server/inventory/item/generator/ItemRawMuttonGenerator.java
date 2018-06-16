package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRawMutton;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRawMuttonGenerator implements ItemGenerator {

   @Override
   public ItemRawMutton generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRawMutton( data, amount, nbt );
   }

   @Override
   public ItemRawMutton generate( short data, byte amount ) {
       return new ItemRawMutton( data, amount );
   }

}
