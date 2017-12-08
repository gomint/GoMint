package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGlowingRedstoneOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGlowingRedstoneOreGenerator implements ItemGenerator {

   @Override
   public ItemGlowingRedstoneOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGlowingRedstoneOre( data, amount, nbt );
   }

   @Override
   public ItemGlowingRedstoneOre generate( short data, byte amount ) {
       return new ItemGlowingRedstoneOre( data, amount );
   }

}
