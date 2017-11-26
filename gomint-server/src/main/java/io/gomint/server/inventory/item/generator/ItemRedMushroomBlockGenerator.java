package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedMushroomBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedMushroomBlockGenerator implements ItemGenerator {

   @Override
   public ItemRedMushroomBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedMushroomBlock( data, amount, nbt );
   }

   @Override
   public ItemRedMushroomBlock generate( short data, byte amount ) {
       return new ItemRedMushroomBlock( data, amount );
   }

}
