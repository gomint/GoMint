package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCobblestoneWall;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCobblestoneWallGenerator implements ItemGenerator {

   @Override
   public ItemCobblestoneWall generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCobblestoneWall( data, amount, nbt );
   }

   @Override
   public ItemCobblestoneWall generate( short data, byte amount ) {
       return new ItemCobblestoneWall( data, amount );
   }

}
