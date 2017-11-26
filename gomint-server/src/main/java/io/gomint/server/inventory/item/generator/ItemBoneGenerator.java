package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBoneGenerator implements ItemGenerator {

   @Override
   public ItemBone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBone( data, amount, nbt );
   }

   @Override
   public ItemBone generate( short data, byte amount ) {
       return new ItemBone( data, amount );
   }

}
