package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWallSign;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWallSignGenerator implements ItemGenerator {

   @Override
   public ItemWallSign generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWallSign( data, amount, nbt );
   }

   @Override
   public ItemWallSign generate( short data, byte amount ) {
       return new ItemWallSign( data, amount );
   }

}
