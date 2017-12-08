package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenApple;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenAppleGenerator implements ItemGenerator {

   @Override
   public ItemGoldenApple generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenApple( data, amount, nbt );
   }

   @Override
   public ItemGoldenApple generate( short data, byte amount ) {
       return new ItemGoldenApple( data, amount );
   }

}
