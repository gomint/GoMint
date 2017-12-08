package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronOre;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronOreGenerator implements ItemGenerator {

   @Override
   public ItemIronOre generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronOre( data, amount, nbt );
   }

   @Override
   public ItemIronOre generate( short data, byte amount ) {
       return new ItemIronOre( data, amount );
   }

}
