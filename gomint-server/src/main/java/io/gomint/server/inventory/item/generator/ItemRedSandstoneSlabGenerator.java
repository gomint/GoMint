package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedSandstoneSlab;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedSandstoneSlabGenerator implements ItemGenerator {

   @Override
   public ItemRedSandstoneSlab generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedSandstoneSlab( data, amount, nbt );
   }

   @Override
   public ItemRedSandstoneSlab generate( short data, byte amount ) {
       return new ItemRedSandstoneSlab( data, amount );
   }

}
