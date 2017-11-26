package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBow;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBowGenerator implements ItemGenerator {

   @Override
   public ItemBow generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBow( data, amount, nbt );
   }

   @Override
   public ItemBow generate( short data, byte amount ) {
       return new ItemBow( data, amount );
   }

}
