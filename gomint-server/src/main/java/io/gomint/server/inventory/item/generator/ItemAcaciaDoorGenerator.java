package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAcaciaDoor;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAcaciaDoorGenerator implements ItemGenerator {

   @Override
   public ItemAcaciaDoor generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAcaciaDoor( data, amount, nbt );
   }

   @Override
   public ItemAcaciaDoor generate( short data, byte amount ) {
       return new ItemAcaciaDoor( data, amount );
   }

}
