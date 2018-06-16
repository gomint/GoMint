package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGlowingObsidian;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGlowingObsidianGenerator implements ItemGenerator {

   @Override
   public ItemGlowingObsidian generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGlowingObsidian( data, amount, nbt );
   }

   @Override
   public ItemGlowingObsidian generate( short data, byte amount ) {
       return new ItemGlowingObsidian( data, amount );
   }

}
