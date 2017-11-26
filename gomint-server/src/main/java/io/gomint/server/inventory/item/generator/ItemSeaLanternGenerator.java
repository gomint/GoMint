package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSeaLantern;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSeaLanternGenerator implements ItemGenerator {

   @Override
   public ItemSeaLantern generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSeaLantern( data, amount, nbt );
   }

   @Override
   public ItemSeaLantern generate( short data, byte amount ) {
       return new ItemSeaLantern( data, amount );
   }

}
