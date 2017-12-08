package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSnowLayer;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSnowLayerGenerator implements ItemGenerator {

   @Override
   public ItemSnowLayer generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSnowLayer( data, amount, nbt );
   }

   @Override
   public ItemSnowLayer generate( short data, byte amount ) {
       return new ItemSnowLayer( data, amount );
   }

}
