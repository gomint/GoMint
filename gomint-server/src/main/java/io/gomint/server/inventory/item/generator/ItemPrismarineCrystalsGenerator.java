package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPrismarineCrystals;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPrismarineCrystalsGenerator implements ItemGenerator {

   @Override
   public ItemPrismarineCrystals generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPrismarineCrystals( data, amount, nbt );
   }

   @Override
   public ItemPrismarineCrystals generate( short data, byte amount ) {
       return new ItemPrismarineCrystals( data, amount );
   }

}
