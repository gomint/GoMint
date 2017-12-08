package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLilyPad;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLilyPadGenerator implements ItemGenerator {

   @Override
   public ItemLilyPad generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLilyPad( data, amount, nbt );
   }

   @Override
   public ItemLilyPad generate( short data, byte amount ) {
       return new ItemLilyPad( data, amount );
   }

}
