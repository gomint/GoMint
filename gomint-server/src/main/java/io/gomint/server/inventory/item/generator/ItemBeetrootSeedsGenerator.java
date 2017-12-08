package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBeetrootSeeds;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBeetrootSeedsGenerator implements ItemGenerator {

   @Override
   public ItemBeetrootSeeds generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBeetrootSeeds( data, amount, nbt );
   }

   @Override
   public ItemBeetrootSeeds generate( short data, byte amount ) {
       return new ItemBeetrootSeeds( data, amount );
   }

}
