package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGlisteringMelon;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGlisteringMelonGenerator implements ItemGenerator {

   @Override
   public ItemGlisteringMelon generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGlisteringMelon( data, amount, nbt );
   }

   @Override
   public ItemGlisteringMelon generate( short data, byte amount ) {
       return new ItemGlisteringMelon( data, amount );
   }

}
