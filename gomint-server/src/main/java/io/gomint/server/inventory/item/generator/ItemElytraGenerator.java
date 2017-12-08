package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemElytra;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemElytraGenerator implements ItemGenerator {

   @Override
   public ItemElytra generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemElytra( data, amount, nbt );
   }

   @Override
   public ItemElytra generate( short data, byte amount ) {
       return new ItemElytra( data, amount );
   }

}
