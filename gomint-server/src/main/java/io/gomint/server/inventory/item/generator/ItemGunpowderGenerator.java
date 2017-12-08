package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGunpowder;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGunpowderGenerator implements ItemGenerator {

   @Override
   public ItemGunpowder generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGunpowder( data, amount, nbt );
   }

   @Override
   public ItemGunpowder generate( short data, byte amount ) {
       return new ItemGunpowder( data, amount );
   }

}
