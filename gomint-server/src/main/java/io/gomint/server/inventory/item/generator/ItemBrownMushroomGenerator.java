package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBrownMushroom;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBrownMushroomGenerator implements ItemGenerator {

   @Override
   public ItemBrownMushroom generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBrownMushroom( data, amount, nbt );
   }

   @Override
   public ItemBrownMushroom generate( short data, byte amount ) {
       return new ItemBrownMushroom( data, amount );
   }

}
