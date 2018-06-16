package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAcaciaWood;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAcaciaWoodGenerator implements ItemGenerator {

   @Override
   public ItemAcaciaWood generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAcaciaWood( data, amount, nbt );
   }

   @Override
   public ItemAcaciaWood generate( short data, byte amount ) {
       return new ItemAcaciaWood( data, amount );
   }

}
