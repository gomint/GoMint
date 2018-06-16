package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSpiderEye;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSpiderEyeGenerator implements ItemGenerator {

   @Override
   public ItemSpiderEye generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSpiderEye( data, amount, nbt );
   }

   @Override
   public ItemSpiderEye generate( short data, byte amount ) {
       return new ItemSpiderEye( data, amount );
   }

}
