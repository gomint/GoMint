package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStainedGlassPane;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStainedGlassPaneGenerator implements ItemGenerator {

   @Override
   public ItemStainedGlassPane generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStainedGlassPane( data, amount, nbt );
   }

   @Override
   public ItemStainedGlassPane generate( short data, byte amount ) {
       return new ItemStainedGlassPane( data, amount );
   }

}
