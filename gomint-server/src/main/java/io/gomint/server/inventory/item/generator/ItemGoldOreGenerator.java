package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldOreGenerator implements ItemGenerator {

   @Override
   public ItemGoldOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldOre( data, amount, nbt );
   }

   @Override
   public ItemGoldOre generate( short data, byte amount ) {
       return new ItemGoldOre( data, amount );
   }

}
