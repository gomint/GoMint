package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCarrotOnAStick;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCarrotOnAStickGenerator implements ItemGenerator {

   @Override
   public ItemCarrotOnAStick generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCarrotOnAStick( data, amount, nbt );
   }

   @Override
   public ItemCarrotOnAStick generate( short data, byte amount ) {
       return new ItemCarrotOnAStick( data, amount );
   }

}
