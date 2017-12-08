package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemInvisibleBedrock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemInvisibleBedrockGenerator implements ItemGenerator {

   @Override
   public ItemInvisibleBedrock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemInvisibleBedrock( data, amount, nbt );
   }

   @Override
   public ItemInvisibleBedrock generate( short data, byte amount ) {
       return new ItemInvisibleBedrock( data, amount );
   }

}
