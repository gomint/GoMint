package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneWire;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneWireGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneWire generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneWire( data, amount, nbt );
   }

   @Override
   public ItemRedstoneWire generate( short data, byte amount ) {
       return new ItemRedstoneWire( data, amount );
   }

}
