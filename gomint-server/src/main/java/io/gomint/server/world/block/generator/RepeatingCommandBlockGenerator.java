/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.generator;

import io.gomint.math.Location;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.AcaciaDoor;
import io.gomint.server.world.block.RepeatingCommandBlock;

/**
 * @author geNAZt
 * @version 1.0
 */
public class RepeatingCommandBlockGenerator implements BlockGenerator {

   @Override
   public RepeatingCommandBlock generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       RepeatingCommandBlock block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public RepeatingCommandBlock generate() {
       return new RepeatingCommandBlock();
   }

}
