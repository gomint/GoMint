package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFlower;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFlowerGenerator implements ItemGenerator {

   @Override
   public ItemFlower generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFlower( data, amount, nbt );
   }

   @Override
   public ItemFlower generate( short data, byte amount ) {
       return new ItemFlower( data, amount );
   }

}
