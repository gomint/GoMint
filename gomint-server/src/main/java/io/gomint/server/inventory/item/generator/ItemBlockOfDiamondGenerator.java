package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockOfDiamond;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockOfDiamondGenerator implements ItemGenerator {

   @Override
   public ItemBlockOfDiamond generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockOfDiamond( data, amount, nbt );
   }

   @Override
   public ItemBlockOfDiamond generate( short data, byte amount ) {
       return new ItemBlockOfDiamond( data, amount );
   }

}
