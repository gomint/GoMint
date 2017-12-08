package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEndGateway;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEndGatewayGenerator implements ItemGenerator {

   @Override
   public ItemEndGateway generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEndGateway( data, amount, nbt );
   }

   @Override
   public ItemEndGateway generate( short data, byte amount ) {
       return new ItemEndGateway( data, amount );
   }

}
