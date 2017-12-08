package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherReactorCore;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherReactorCoreGenerator implements ItemGenerator {

   @Override
   public ItemNetherReactorCore generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherReactorCore( data, amount, nbt );
   }

   @Override
   public ItemNetherReactorCore generate( short data, byte amount ) {
       return new ItemNetherReactorCore( data, amount );
   }

}
