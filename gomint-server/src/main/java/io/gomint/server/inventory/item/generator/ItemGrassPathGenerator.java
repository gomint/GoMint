package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGrassPath;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGrassPathGenerator implements ItemGenerator {

   @Override
   public ItemGrassPath generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGrassPath( data, amount, nbt );
   }

   @Override
   public ItemGrassPath generate( short data, byte amount ) {
       return new ItemGrassPath( data, amount );
   }

}
