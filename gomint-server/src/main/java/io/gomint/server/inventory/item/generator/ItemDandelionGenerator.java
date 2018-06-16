package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDandelion;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDandelionGenerator implements ItemGenerator {

   @Override
   public ItemDandelion generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDandelion( data, amount, nbt );
   }

   @Override
   public ItemDandelion generate( short data, byte amount ) {
       return new ItemDandelion( data, amount );
   }

}
