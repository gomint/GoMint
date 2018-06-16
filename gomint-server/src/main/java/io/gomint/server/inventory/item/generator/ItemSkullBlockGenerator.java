package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSkullBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSkullBlockGenerator implements ItemGenerator {

   @Override
   public ItemSkullBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSkullBlock( data, amount, nbt );
   }

   @Override
   public ItemSkullBlock generate( short data, byte amount ) {
       return new ItemSkullBlock( data, amount );
   }

}
