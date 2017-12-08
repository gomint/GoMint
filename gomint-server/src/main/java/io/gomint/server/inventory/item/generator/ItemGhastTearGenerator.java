package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGhastTear;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGhastTearGenerator implements ItemGenerator {

   @Override
   public ItemGhastTear generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGhastTear( data, amount, nbt );
   }

   @Override
   public ItemGhastTear generate( short data, byte amount ) {
       return new ItemGhastTear( data, amount );
   }

}
