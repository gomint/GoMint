package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPoisonousPotato;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPoisonousPotatoGenerator implements ItemGenerator {

   @Override
   public ItemPoisonousPotato generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPoisonousPotato( data, amount, nbt );
   }

   @Override
   public ItemPoisonousPotato generate( short data, byte amount ) {
       return new ItemPoisonousPotato( data, amount );
   }

}
