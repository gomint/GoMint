package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFlintAndSteel;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFlintAndSteelGenerator implements ItemGenerator {

   @Override
   public ItemFlintAndSteel generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFlintAndSteel( data, amount, nbt );
   }

   @Override
   public ItemFlintAndSteel generate( short data, byte amount ) {
       return new ItemFlintAndSteel( data, amount );
   }

}
