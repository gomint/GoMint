package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNameTag;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNameTagGenerator implements ItemGenerator {

   @Override
   public ItemNameTag generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNameTag( data, amount, nbt );
   }

   @Override
   public ItemNameTag generate( short data, byte amount ) {
       return new ItemNameTag( data, amount );
   }

}
