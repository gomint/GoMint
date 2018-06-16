package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEmerald;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEmeraldGenerator implements ItemGenerator {

   @Override
   public ItemEmerald generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEmerald( data, amount, nbt );
   }

   @Override
   public ItemEmerald generate( short data, byte amount ) {
       return new ItemEmerald( data, amount );
   }

}
