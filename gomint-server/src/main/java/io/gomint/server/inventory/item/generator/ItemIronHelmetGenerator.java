package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronHelmet;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronHelmetGenerator implements ItemGenerator {

   @Override
   public ItemIronHelmet generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronHelmet( data, amount, nbt );
   }

   @Override
   public ItemIronHelmet generate( short data, byte amount ) {
       return new ItemIronHelmet( data, amount );
   }

}
