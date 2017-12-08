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
import io.gomint.server.world.block.NetherWart;
import io.gomint.server.world.block.NetherWartBlock;

/**
 * @author geNAZt
 * @version 1.0
 */
public class NetherWartBlockGenerator implements BlockGenerator {

   @Override
   public NetherWartBlock generate( byte blockData, byte skyLightLevel, byte blockLightLevel, TileEntity tileEntity, Location location ) {
       NetherWartBlock block = generate();
       block.setData( blockData, tileEntity, (WorldAdapter) location.getWorld(), location, skyLightLevel, blockLightLevel );
       return block;
   }

   @Override
   public NetherWartBlock generate() {
       return new NetherWartBlock();
   }

}
