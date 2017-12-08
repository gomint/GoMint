package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherQuartzOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherQuartzOreGenerator implements ItemGenerator {

   @Override
   public ItemNetherQuartzOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherQuartzOre( data, amount, nbt );
   }

   @Override
   public ItemNetherQuartzOre generate( short data, byte amount ) {
       return new ItemNetherQuartzOre( data, amount );
   }

}
