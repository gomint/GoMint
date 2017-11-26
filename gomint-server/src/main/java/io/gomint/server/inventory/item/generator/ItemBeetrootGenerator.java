package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBeetroot;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBeetrootGenerator implements ItemGenerator {

   @Override
   public ItemBeetroot generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBeetroot( data, amount, nbt );
   }

   @Override
   public ItemBeetroot generate( short data, byte amount ) {
       return new ItemBeetroot( data, amount );
   }

}
