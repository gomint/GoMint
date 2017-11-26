package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAcaciaLeaves;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAcaciaLeavesGenerator implements ItemGenerator {

   @Override
   public ItemAcaciaLeaves generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAcaciaLeaves( data, amount, nbt );
   }

   @Override
   public ItemAcaciaLeaves generate( short data, byte amount ) {
       return new ItemAcaciaLeaves( data, amount );
   }

}
