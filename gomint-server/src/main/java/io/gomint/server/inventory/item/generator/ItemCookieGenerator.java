package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCookie;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCookieGenerator implements ItemGenerator {

   @Override
   public ItemCookie generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCookie( data, amount, nbt );
   }

   @Override
   public ItemCookie generate( short data, byte amount ) {
       return new ItemCookie( data, amount );
   }

}
