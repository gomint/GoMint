package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPodzol;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPodzolGenerator implements ItemGenerator {

   @Override
   public ItemPodzol generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPodzol( data, amount, nbt );
   }

   @Override
   public ItemPodzol generate( short data, byte amount ) {
       return new ItemPodzol( data, amount );
   }

}
