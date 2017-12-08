package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBrick;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBrickGenerator implements ItemGenerator {

   @Override
   public ItemBrick generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBrick( data, amount, nbt );
   }

   @Override
   public ItemBrick generate( short data, byte amount ) {
       return new ItemBrick( data, amount );
   }

}
