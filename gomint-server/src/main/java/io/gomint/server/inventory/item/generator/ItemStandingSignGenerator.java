package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStandingSign;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStandingSignGenerator implements ItemGenerator {

   @Override
   public ItemStandingSign generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStandingSign( data, amount, nbt );
   }

   @Override
   public ItemStandingSign generate( short data, byte amount ) {
       return new ItemStandingSign( data, amount );
   }

}
