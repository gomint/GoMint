package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemJungleDoorBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemJungleDoorBlockGenerator implements ItemGenerator {

   @Override
   public ItemJungleDoorBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemJungleDoorBlock( data, amount, nbt );
   }

   @Override
   public ItemJungleDoorBlock generate( short data, byte amount ) {
       return new ItemJungleDoorBlock( data, amount );
   }

}
