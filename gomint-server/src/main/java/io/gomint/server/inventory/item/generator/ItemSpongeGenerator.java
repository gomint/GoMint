package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSponge;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSpongeGenerator implements ItemGenerator {

   @Override
   public ItemSponge generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSponge( data, amount, nbt );
   }

   @Override
   public ItemSponge generate( short data, byte amount ) {
       return new ItemSponge( data, amount );
   }

}
