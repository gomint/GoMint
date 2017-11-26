package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEyeOfEnder;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEyeOfEnderGenerator implements ItemGenerator {

   @Override
   public ItemEyeOfEnder generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEyeOfEnder( data, amount, nbt );
   }

   @Override
   public ItemEyeOfEnder generate( short data, byte amount ) {
       return new ItemEyeOfEnder( data, amount );
   }

}
