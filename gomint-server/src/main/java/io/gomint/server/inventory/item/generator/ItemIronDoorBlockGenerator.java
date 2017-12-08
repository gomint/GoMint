package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronDoorBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronDoorBlockGenerator implements ItemGenerator {

   @Override
   public ItemIronDoorBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronDoorBlock( data, amount, nbt );
   }

   @Override
   public ItemIronDoorBlock generate( short data, byte amount ) {
       return new ItemIronDoorBlock( data, amount );
   }

}
