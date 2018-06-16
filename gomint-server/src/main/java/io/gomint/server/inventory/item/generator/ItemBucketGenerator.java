package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBucket;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBucketGenerator implements ItemGenerator {

   @Override
   public ItemBucket generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBucket( data, amount, nbt );
   }

   @Override
   public ItemBucket generate( short data, byte amount ) {
       return new ItemBucket( data, amount );
   }

}
