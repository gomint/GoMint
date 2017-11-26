package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBakedPotato;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBakedPotatoGenerator implements ItemGenerator {

   @Override
   public ItemBakedPotato generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBakedPotato( data, amount, nbt );
   }

   @Override
   public ItemBakedPotato generate( short data, byte amount ) {
       return new ItemBakedPotato( data, amount );
   }

}
