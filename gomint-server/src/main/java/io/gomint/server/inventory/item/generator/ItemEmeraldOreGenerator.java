package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEmeraldOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEmeraldOreGenerator implements ItemGenerator {

   @Override
   public ItemEmeraldOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEmeraldOre( data, amount, nbt );
   }

   @Override
   public ItemEmeraldOre generate( short data, byte amount ) {
       return new ItemEmeraldOre( data, amount );
   }

}
