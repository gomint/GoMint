package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenHelmet;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenHelmetGenerator implements ItemGenerator {

   @Override
   public ItemGoldenHelmet generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenHelmet( data, amount, nbt );
   }

   @Override
   public ItemGoldenHelmet generate( short data, byte amount ) {
       return new ItemGoldenHelmet( data, amount );
   }

}
