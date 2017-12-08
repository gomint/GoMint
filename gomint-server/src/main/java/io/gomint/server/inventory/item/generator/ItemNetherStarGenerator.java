package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherStar;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherStarGenerator implements ItemGenerator {

   @Override
   public ItemNetherStar generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherStar( data, amount, nbt );
   }

   @Override
   public ItemNetherStar generate( short data, byte amount ) {
       return new ItemNetherStar( data, amount );
   }

}
