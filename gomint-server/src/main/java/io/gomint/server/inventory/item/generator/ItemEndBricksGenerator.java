package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEndBricks;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEndBricksGenerator implements ItemGenerator {

   @Override
   public ItemEndBricks generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEndBricks( data, amount, nbt );
   }

   @Override
   public ItemEndBricks generate( short data, byte amount ) {
       return new ItemEndBricks( data, amount );
   }

}
