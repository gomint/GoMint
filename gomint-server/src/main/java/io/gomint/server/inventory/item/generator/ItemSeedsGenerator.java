package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSeeds;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSeedsGenerator implements ItemGenerator {

   @Override
   public ItemSeeds generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSeeds( data, amount, nbt );
   }

   @Override
   public ItemSeeds generate( short data, byte amount ) {
       return new ItemSeeds( data, amount );
   }

}
