package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemMinecartWithChest;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemMinecartWithChestGenerator implements ItemGenerator {

   @Override
   public ItemMinecartWithChest generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemMinecartWithChest( data, amount, nbt );
   }

   @Override
   public ItemMinecartWithChest generate( short data, byte amount ) {
       return new ItemMinecartWithChest( data, amount );
   }

}
