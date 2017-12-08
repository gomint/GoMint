package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenHorseArmor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenHorseArmorGenerator implements ItemGenerator {

   @Override
   public ItemGoldenHorseArmor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenHorseArmor( data, amount, nbt );
   }

   @Override
   public ItemGoldenHorseArmor generate( short data, byte amount ) {
       return new ItemGoldenHorseArmor( data, amount );
   }

}
