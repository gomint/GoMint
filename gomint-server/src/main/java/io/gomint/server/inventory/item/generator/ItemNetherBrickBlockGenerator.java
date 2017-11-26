package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherBrickBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherBrickBlockGenerator implements ItemGenerator {

   @Override
   public ItemNetherBrickBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherBrickBlock( data, amount, nbt );
   }

   @Override
   public ItemNetherBrickBlock generate( short data, byte amount ) {
       return new ItemNetherBrickBlock( data, amount );
   }

}
