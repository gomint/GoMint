package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBirchDoorBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBirchDoorBlockGenerator implements ItemGenerator {

   @Override
   public ItemBirchDoorBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBirchDoorBlock( data, amount, nbt );
   }

   @Override
   public ItemBirchDoorBlock generate( short data, byte amount ) {
       return new ItemBirchDoorBlock( data, amount );
   }

}
