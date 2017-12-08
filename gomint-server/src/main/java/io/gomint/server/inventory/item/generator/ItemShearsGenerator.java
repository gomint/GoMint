package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemShears;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemShearsGenerator implements ItemGenerator {

   @Override
   public ItemShears generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemShears( data, amount, nbt );
   }

   @Override
   public ItemShears generate( short data, byte amount ) {
       return new ItemShears( data, amount );
   }

}
