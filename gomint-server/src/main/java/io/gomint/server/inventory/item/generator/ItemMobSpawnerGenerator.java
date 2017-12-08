package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMobSpawner;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMobSpawnerGenerator implements ItemGenerator {

   @Override
   public ItemMobSpawner generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMobSpawner( data, amount, nbt );
   }

   @Override
   public ItemMobSpawner generate( short data, byte amount ) {
       return new ItemMobSpawner( data, amount );
   }

}
