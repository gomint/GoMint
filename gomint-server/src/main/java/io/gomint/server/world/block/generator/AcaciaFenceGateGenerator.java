package io.gomint.server.world.block.generator;

import io.gomint.server.world.block.AcaciaFenceGate;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.world.WorldAdapter;
import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AcaciaFenceGateGenerator implements BlockGenerator {

   @Override
   public AcaciaFenceGate generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       AcaciaFenceGate block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public AcaciaFenceGate generate() {
       return new AcaciaFenceGate();
   }

}
