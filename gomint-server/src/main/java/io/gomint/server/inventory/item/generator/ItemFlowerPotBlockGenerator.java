package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFlowerPotBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFlowerPotBlockGenerator implements ItemGenerator {

   @Override
   public ItemFlowerPotBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFlowerPotBlock( data, amount, nbt );
   }

   @Override
   public ItemFlowerPotBlock generate( short data, byte amount ) {
       return new ItemFlowerPotBlock( data, amount );
   }

}
