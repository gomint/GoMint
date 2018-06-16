package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLeatherHelmet;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeatherHelmetGenerator implements ItemGenerator {

   @Override
   public ItemLeatherHelmet generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLeatherHelmet( data, amount, nbt );
   }

   @Override
   public ItemLeatherHelmet generate( short data, byte amount ) {
       return new ItemLeatherHelmet( data, amount );
   }

}
