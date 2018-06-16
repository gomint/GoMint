package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemTallGrass;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemTallGrassGenerator implements ItemGenerator {

   @Override
   public ItemTallGrass generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemTallGrass( data, amount, nbt );
   }

   @Override
   public ItemTallGrass generate( short data, byte amount ) {
       return new ItemTallGrass( data, amount );
   }

}
