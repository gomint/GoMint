package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBedBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBedBlockGenerator implements ItemGenerator {

   @Override
   public ItemBedBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBedBlock( data, amount, nbt );
   }

   @Override
   public ItemBedBlock generate( short data, byte amount ) {
       return new ItemBedBlock( data, amount );
   }

}
