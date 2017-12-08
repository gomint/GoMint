package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWool;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoolGenerator implements ItemGenerator {

   @Override
   public ItemWool generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWool( data, amount, nbt );
   }

   @Override
   public ItemWool generate( short data, byte amount ) {
       return new ItemWool( data, amount );
   }

}
