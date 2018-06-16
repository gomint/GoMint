package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondHelmet;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondHelmetGenerator implements ItemGenerator {

   @Override
   public ItemDiamondHelmet generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondHelmet( data, amount, nbt );
   }

   @Override
   public ItemDiamondHelmet generate( short data, byte amount ) {
       return new ItemDiamondHelmet( data, amount );
   }

}
