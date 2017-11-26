package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherBrick;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherBrickGenerator implements ItemGenerator {

   @Override
   public ItemNetherBrick generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherBrick( data, amount, nbt );
   }

   @Override
   public ItemNetherBrick generate( short data, byte amount ) {
       return new ItemNetherBrick( data, amount );
   }

}
