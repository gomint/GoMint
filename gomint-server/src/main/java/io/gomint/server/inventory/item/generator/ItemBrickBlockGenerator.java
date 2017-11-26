package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBrickBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBrickBlockGenerator implements ItemGenerator {

   @Override
   public ItemBrickBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBrickBlock( data, amount, nbt );
   }

   @Override
   public ItemBrickBlock generate( short data, byte amount ) {
       return new ItemBrickBlock( data, amount );
   }

}
