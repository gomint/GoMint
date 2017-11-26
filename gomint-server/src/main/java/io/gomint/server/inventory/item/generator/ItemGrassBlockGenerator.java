package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGrassBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGrassBlockGenerator implements ItemGenerator {

   @Override
   public ItemGrassBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGrassBlock( data, amount, nbt );
   }

   @Override
   public ItemGrassBlock generate( short data, byte amount ) {
       return new ItemGrassBlock( data, amount );
   }

}
