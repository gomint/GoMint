package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMelon;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMelonGenerator implements ItemGenerator {

   @Override
   public ItemMelon generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMelon( data, amount, nbt );
   }

   @Override
   public ItemMelon generate( short data, byte amount ) {
       return new ItemMelon( data, amount );
   }

}
