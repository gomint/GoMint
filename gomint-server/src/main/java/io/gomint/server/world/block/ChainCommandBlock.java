/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.entity.tileentity.BannerTileEntity;
import io.gomint.server.entity.tileentity.CommandBlockTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockChainCommandBlock;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:chain_command_block" )
public class ChainCommandBlock extends ContainerBlock implements BlockChainCommandBlock {

    @Override
    public String getBlockId() {
        return "minecraft:chain_command_block";
    }

    @Override
    public float getBlastResistance() {
        return 18000000.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHAIN_COMMAND_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        super.createTileEntity( compound );
        return this.tileEntities.construct(CommandBlockTileEntity.class, compound, this, this.items);
    }

}
