package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMinecartWithTnt;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMinecartWithTntGenerator implements ItemGenerator {

   @Override
   public ItemMinecartWithTnt generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMinecartWithTnt( data, amount, nbt );
   }

   @Override
   public ItemMinecartWithTnt generate( short data, byte amount ) {
       return new ItemMinecartWithTnt( data, amount );
   }

}
