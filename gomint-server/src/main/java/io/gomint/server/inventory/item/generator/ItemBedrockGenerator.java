package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBedrock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBedrockGenerator implements ItemGenerator {

   @Override
   public ItemBedrock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBedrock( data, amount, nbt );
   }

   @Override
   public ItemBedrock generate( short data, byte amount ) {
       return new ItemBedrock( data, amount );
   }

}
