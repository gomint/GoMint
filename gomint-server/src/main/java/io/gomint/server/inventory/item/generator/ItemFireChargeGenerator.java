package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFireCharge;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFireChargeGenerator implements ItemGenerator {

   @Override
   public ItemFireCharge generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFireCharge( data, amount, nbt );
   }

   @Override
   public ItemFireCharge generate( short data, byte amount ) {
       return new ItemFireCharge( data, amount );
   }

}
