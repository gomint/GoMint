package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLeaves;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeavesGenerator implements ItemGenerator {

   @Override
   public ItemLeaves generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLeaves( data, amount, nbt );
   }

   @Override
   public ItemLeaves generate( short data, byte amount ) {
       return new ItemLeaves( data, amount );
   }

}
