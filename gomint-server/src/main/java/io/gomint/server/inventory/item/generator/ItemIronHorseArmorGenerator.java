package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronHorseArmor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronHorseArmorGenerator implements ItemGenerator {

   @Override
   public ItemIronHorseArmor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronHorseArmor( data, amount, nbt );
   }

   @Override
   public ItemIronHorseArmor generate( short data, byte amount ) {
       return new ItemIronHorseArmor( data, amount );
   }

}
