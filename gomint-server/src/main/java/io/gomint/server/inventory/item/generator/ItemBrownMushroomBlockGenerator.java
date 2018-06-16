package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBrownMushroomBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBrownMushroomBlockGenerator implements ItemGenerator {

   @Override
   public ItemBrownMushroomBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBrownMushroomBlock( data, amount, nbt );
   }

   @Override
   public ItemBrownMushroomBlock generate( short data, byte amount ) {
       return new ItemBrownMushroomBlock( data, amount );
   }

}
