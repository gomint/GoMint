package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemWoodenDoorBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemWoodenDoorBlockGenerator implements ItemGenerator {

   @Override
   public ItemWoodenDoorBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemWoodenDoorBlock( data, amount, nbt );
   }

   @Override
   public ItemWoodenDoorBlock generate( short data, byte amount ) {
       return new ItemWoodenDoorBlock( data, amount );
   }

}
