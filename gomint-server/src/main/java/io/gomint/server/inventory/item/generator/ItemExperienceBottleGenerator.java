package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemExperienceBottle;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemExperienceBottleGenerator implements ItemGenerator {

   @Override
   public ItemExperienceBottle generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemExperienceBottle( data, amount, nbt );
   }

   @Override
   public ItemExperienceBottle generate( short data, byte amount ) {
       return new ItemExperienceBottle( data, amount );
   }

}
