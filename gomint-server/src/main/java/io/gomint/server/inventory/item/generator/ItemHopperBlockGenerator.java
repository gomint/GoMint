package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemHopperBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemHopperBlockGenerator implements ItemGenerator {

   @Override
   public ItemHopperBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemHopperBlock( data, amount, nbt );
   }

   @Override
   public ItemHopperBlock generate( short data, byte amount ) {
       return new ItemHopperBlock( data, amount );
   }

}
