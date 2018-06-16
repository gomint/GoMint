package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPistonHead;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPistonHeadGenerator implements ItemGenerator {

   @Override
   public ItemPistonHead generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPistonHead( data, amount, nbt );
   }

   @Override
   public ItemPistonHead generate( short data, byte amount ) {
       return new ItemPistonHead( data, amount );
   }

}
