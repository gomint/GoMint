package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLadder;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLadderGenerator implements ItemGenerator {

   @Override
   public ItemLadder generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLadder( data, amount, nbt );
   }

   @Override
   public ItemLadder generate( short data, byte amount ) {
       return new ItemLadder( data, amount );
   }

}
