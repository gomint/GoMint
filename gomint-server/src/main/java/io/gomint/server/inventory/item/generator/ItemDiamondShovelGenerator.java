package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondShovel;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondShovelGenerator implements ItemGenerator {

   @Override
   public ItemDiamondShovel generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondShovel( data, amount, nbt );
   }

   @Override
   public ItemDiamondShovel generate( short data, byte amount ) {
       return new ItemDiamondShovel( data, amount );
   }

}
