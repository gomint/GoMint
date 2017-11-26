package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlazePowder;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlazePowderGenerator implements ItemGenerator {

   @Override
   public ItemBlazePowder generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlazePowder( data, amount, nbt );
   }

   @Override
   public ItemBlazePowder generate( short data, byte amount ) {
       return new ItemBlazePowder( data, amount );
   }

}
