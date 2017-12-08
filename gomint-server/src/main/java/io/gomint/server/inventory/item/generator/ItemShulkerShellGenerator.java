package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemShulkerShell;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemShulkerShellGenerator implements ItemGenerator {

   @Override
   public ItemShulkerShell generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemShulkerShell( data, amount, nbt );
   }

   @Override
   public ItemShulkerShell generate( short data, byte amount ) {
       return new ItemShulkerShell( data, amount );
   }

}
