package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemJackOLantern;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemJackOLanternGenerator implements ItemGenerator {

   @Override
   public ItemJackOLantern generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemJackOLantern( data, amount, nbt );
   }

   @Override
   public ItemJackOLantern generate( short data, byte amount ) {
       return new ItemJackOLantern( data, amount );
   }

}
