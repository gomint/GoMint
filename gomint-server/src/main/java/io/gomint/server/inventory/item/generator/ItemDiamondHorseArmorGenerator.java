package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondHorseArmor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondHorseArmorGenerator implements ItemGenerator {

   @Override
   public ItemDiamondHorseArmor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondHorseArmor( data, amount, nbt );
   }

   @Override
   public ItemDiamondHorseArmor generate( short data, byte amount ) {
       return new ItemDiamondHorseArmor( data, amount );
   }

}
