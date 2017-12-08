package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenAxe;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenAxeGenerator implements ItemGenerator {

   @Override
   public ItemGoldenAxe generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenAxe( data, amount, nbt );
   }

   @Override
   public ItemGoldenAxe generate( short data, byte amount ) {
       return new ItemGoldenAxe( data, amount );
   }

}
