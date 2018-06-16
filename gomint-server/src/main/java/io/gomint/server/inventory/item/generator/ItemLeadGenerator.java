package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLead;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeadGenerator implements ItemGenerator {

   @Override
   public ItemLead generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLead( data, amount, nbt );
   }

   @Override
   public ItemLead generate( short data, byte amount ) {
       return new ItemLead( data, amount );
   }

}
