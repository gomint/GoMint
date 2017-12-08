package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamond;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondGenerator implements ItemGenerator {

   @Override
   public ItemDiamond generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamond( data, amount, nbt );
   }

   @Override
   public ItemDiamond generate( short data, byte amount ) {
       return new ItemDiamond( data, amount );
   }

}
