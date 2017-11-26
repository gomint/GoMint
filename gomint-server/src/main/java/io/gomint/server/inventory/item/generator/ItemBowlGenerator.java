package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBowl;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBowlGenerator implements ItemGenerator {

   @Override
   public ItemBowl generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBowl( data, amount, nbt );
   }

   @Override
   public ItemBowl generate( short data, byte amount ) {
       return new ItemBowl( data, amount );
   }

}
