package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneOreGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneOre( data, amount, nbt );
   }

   @Override
   public ItemRedstoneOre generate( short data, byte amount ) {
       return new ItemRedstoneOre( data, amount );
   }

}
