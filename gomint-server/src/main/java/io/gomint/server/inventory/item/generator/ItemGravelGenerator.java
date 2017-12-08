package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGravel;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGravelGenerator implements ItemGenerator {

   @Override
   public ItemGravel generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGravel( data, amount, nbt );
   }

   @Override
   public ItemGravel generate( short data, byte amount ) {
       return new ItemGravel( data, amount );
   }

}
