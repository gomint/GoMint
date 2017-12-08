package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMelonSeeds;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMelonSeedsGenerator implements ItemGenerator {

   @Override
   public ItemMelonSeeds generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMelonSeeds( data, amount, nbt );
   }

   @Override
   public ItemMelonSeeds generate( short data, byte amount ) {
       return new ItemMelonSeeds( data, amount );
   }

}
