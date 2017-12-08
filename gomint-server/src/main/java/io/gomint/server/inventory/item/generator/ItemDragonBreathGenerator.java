package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDragonBreath;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDragonBreathGenerator implements ItemGenerator {

   @Override
   public ItemDragonBreath generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDragonBreath( data, amount, nbt );
   }

   @Override
   public ItemDragonBreath generate( short data, byte amount ) {
       return new ItemDragonBreath( data, amount );
   }

}
