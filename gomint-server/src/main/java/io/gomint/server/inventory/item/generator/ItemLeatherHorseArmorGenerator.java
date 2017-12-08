package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLeatherHorseArmor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeatherHorseArmorGenerator implements ItemGenerator {

   @Override
   public ItemLeatherHorseArmor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLeatherHorseArmor( data, amount, nbt );
   }

   @Override
   public ItemLeatherHorseArmor generate( short data, byte amount ) {
       return new ItemLeatherHorseArmor( data, amount );
   }

}
