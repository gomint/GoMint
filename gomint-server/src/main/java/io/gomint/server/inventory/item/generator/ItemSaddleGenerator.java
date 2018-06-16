package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSaddle;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSaddleGenerator implements ItemGenerator {

   @Override
   public ItemSaddle generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSaddle( data, amount, nbt );
   }

   @Override
   public ItemSaddle generate( short data, byte amount ) {
       return new ItemSaddle( data, amount );
   }

}
