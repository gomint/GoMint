package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondSword;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondSwordGenerator implements ItemGenerator {

   @Override
   public ItemDiamondSword generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondSword( data, amount, nbt );
   }

   @Override
   public ItemDiamondSword generate( short data, byte amount ) {
       return new ItemDiamondSword( data, amount );
   }

}
