package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDetectorRail;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDetectorRailGenerator implements ItemGenerator {

   @Override
   public ItemDetectorRail generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDetectorRail( data, amount, nbt );
   }

   @Override
   public ItemDetectorRail generate( short data, byte amount ) {
       return new ItemDetectorRail( data, amount );
   }

}
