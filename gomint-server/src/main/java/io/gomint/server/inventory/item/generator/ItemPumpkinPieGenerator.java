package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPumpkinPie;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPumpkinPieGenerator implements ItemGenerator {

   @Override
   public ItemPumpkinPie generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPumpkinPie( data, amount, nbt );
   }

   @Override
   public ItemPumpkinPie generate( short data, byte amount ) {
       return new ItemPumpkinPie( data, amount );
   }

}
