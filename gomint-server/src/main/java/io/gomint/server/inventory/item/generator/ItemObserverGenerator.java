package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemObserver;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemObserverGenerator implements ItemGenerator {

   @Override
   public ItemObserver generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemObserver( data, amount, nbt );
   }

   @Override
   public ItemObserver generate( short data, byte amount ) {
       return new ItemObserver( data, amount );
   }

}
