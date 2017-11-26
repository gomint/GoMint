package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockOfEmerald;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockOfEmeraldGenerator implements ItemGenerator {

   @Override
   public ItemBlockOfEmerald generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockOfEmerald( data, amount, nbt );
   }

   @Override
   public ItemBlockOfEmerald generate( short data, byte amount ) {
       return new ItemBlockOfEmerald( data, amount );
   }

}
