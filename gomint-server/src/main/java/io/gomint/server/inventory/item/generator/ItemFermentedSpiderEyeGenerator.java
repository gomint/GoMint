package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFermentedSpiderEye;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFermentedSpiderEyeGenerator implements ItemGenerator {

   @Override
   public ItemFermentedSpiderEye generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFermentedSpiderEye( data, amount, nbt );
   }

   @Override
   public ItemFermentedSpiderEye generate( short data, byte amount ) {
       return new ItemFermentedSpiderEye( data, amount );
   }

}
