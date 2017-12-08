package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenSword;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenSwordGenerator implements ItemGenerator {

   @Override
   public ItemGoldenSword generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenSword( data, amount, nbt );
   }

   @Override
   public ItemGoldenSword generate( short data, byte amount ) {
       return new ItemGoldenSword( data, amount );
   }

}
