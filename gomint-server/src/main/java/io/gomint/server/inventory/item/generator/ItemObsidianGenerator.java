package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemObsidian;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemObsidianGenerator implements ItemGenerator {

   @Override
   public ItemObsidian generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemObsidian( data, amount, nbt );
   }

   @Override
   public ItemObsidian generate( short data, byte amount ) {
       return new ItemObsidian( data, amount );
   }

}
