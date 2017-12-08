package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRottenFlesh;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRottenFleshGenerator implements ItemGenerator {

   @Override
   public ItemRottenFlesh generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRottenFlesh( data, amount, nbt );
   }

   @Override
   public ItemRottenFlesh generate( short data, byte amount ) {
       return new ItemRottenFlesh( data, amount );
   }

}
