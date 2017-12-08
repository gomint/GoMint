package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronSword;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronSwordGenerator implements ItemGenerator {

   @Override
   public ItemIronSword generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronSword( data, amount, nbt );
   }

   @Override
   public ItemIronSword generate( short data, byte amount ) {
       return new ItemIronSword( data, amount );
   }

}
