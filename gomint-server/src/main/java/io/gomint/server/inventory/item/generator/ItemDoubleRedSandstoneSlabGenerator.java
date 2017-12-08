package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDoubleRedSandstoneSlab;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDoubleRedSandstoneSlabGenerator implements ItemGenerator {

   @Override
   public ItemDoubleRedSandstoneSlab generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDoubleRedSandstoneSlab( data, amount, nbt );
   }

   @Override
   public ItemDoubleRedSandstoneSlab generate( short data, byte amount ) {
       return new ItemDoubleRedSandstoneSlab( data, amount );
   }

}
