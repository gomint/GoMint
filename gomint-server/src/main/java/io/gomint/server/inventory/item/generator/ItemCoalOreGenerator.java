package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCoalOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCoalOreGenerator implements ItemGenerator {

   @Override
   public ItemCoalOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCoalOre( data, amount, nbt );
   }

   @Override
   public ItemCoalOre generate( short data, byte amount ) {
       return new ItemCoalOre( data, amount );
   }

}
