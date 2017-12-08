package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPumpkinSeeds;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPumpkinSeedsGenerator implements ItemGenerator {

   @Override
   public ItemPumpkinSeeds generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPumpkinSeeds( data, amount, nbt );
   }

   @Override
   public ItemPumpkinSeeds generate( short data, byte amount ) {
       return new ItemPumpkinSeeds( data, amount );
   }

}
