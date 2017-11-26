package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGlassPane;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGlassPaneGenerator implements ItemGenerator {

   @Override
   public ItemGlassPane generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGlassPane( data, amount, nbt );
   }

   @Override
   public ItemGlassPane generate( short data, byte amount ) {
       return new ItemGlassPane( data, amount );
   }

}
