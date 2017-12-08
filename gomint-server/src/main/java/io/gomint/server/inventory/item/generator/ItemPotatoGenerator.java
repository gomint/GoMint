package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPotato;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPotatoGenerator implements ItemGenerator {

   @Override
   public ItemPotato generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPotato( data, amount, nbt );
   }

   @Override
   public ItemPotato generate( short data, byte amount ) {
       return new ItemPotato( data, amount );
   }

}
