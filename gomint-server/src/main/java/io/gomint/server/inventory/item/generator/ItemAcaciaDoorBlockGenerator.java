package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemAcaciaDoorBlock;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemAcaciaDoorBlockGenerator implements ItemGenerator {

   @Override
   public ItemAcaciaDoorBlock generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemAcaciaDoorBlock( data, amount, nbt );
   }

   @Override
   public ItemAcaciaDoorBlock generate( short data, byte amount ) {
       return new ItemAcaciaDoorBlock( data, amount );
   }

}
