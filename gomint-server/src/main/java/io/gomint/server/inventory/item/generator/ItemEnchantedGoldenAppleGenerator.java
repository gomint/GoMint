package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEnchantedGoldenApple;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEnchantedGoldenAppleGenerator implements ItemGenerator {

   @Override
   public ItemEnchantedGoldenApple generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEnchantedGoldenApple( data, amount, nbt );
   }

   @Override
   public ItemEnchantedGoldenApple generate( short data, byte amount ) {
       return new ItemEnchantedGoldenApple( data, amount );
   }

}
