package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBeetrootBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBeetrootBlockGenerator implements ItemGenerator {

   @Override
   public ItemBeetrootBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBeetrootBlock( data, amount, nbt );
   }

   @Override
   public ItemBeetrootBlock generate( short data, byte amount ) {
       return new ItemBeetrootBlock( data, amount );
   }

}
