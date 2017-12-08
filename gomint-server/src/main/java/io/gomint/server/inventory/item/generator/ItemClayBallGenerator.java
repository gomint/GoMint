package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemClayBall;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemClayBallGenerator implements ItemGenerator {

   @Override
   public ItemClayBall generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemClayBall( data, amount, nbt );
   }

   @Override
   public ItemClayBall generate( short data, byte amount ) {
       return new ItemClayBall( data, amount );
   }

}
