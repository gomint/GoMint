/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.math.BlockPosition;
import io.gomint.server.util.DumpUtil;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@ToString
public class PistonArmTileEntity extends TileEntity {

    // States
    private byte state;
    private byte newState;

    // Progress
    private float progess;
    private float lastProgress;

    // Block tracker
    private List<Object> attachedBlocks = new ArrayList<>();
    private List<Object> breakBlocks = new ArrayList<>();

    // Sticky
    private byte sticky;

    /**
     * Construct new TileEntity from TagCompound
     *
     * @param tagCompound The TagCompound which should be used to read data from
     * @param world       The world in which this TileEntity resides
     */
    public PistonArmTileEntity( NBTTagCompound tagCompound, WorldAdapter world ) {
        super( tagCompound, world );

        this.state = tagCompound.getByte( "State", (byte) 0 );
        this.newState = tagCompound.getByte( "NewState", (byte) 0 );

        this.progess = tagCompound.getFloat( "Progress", 0f );
        this.lastProgress = tagCompound.getFloat( "LastProgress", 0f );

        this.attachedBlocks = tagCompound.getList( "AttachedBlocks", false );
        this.breakBlocks = tagCompound.getList( "BreakBlocks", false );

        this.sticky = tagCompound.getByte( "Sticky", (byte) 0 );
    }

    @Override
    public void update( long currentMillis, float dF ) {

    }

    @Override
    public void toCompound( NBTTagCompound compound ) {
        super.toCompound( compound );
        compound.addValue( "id", "PistonArm" );

        // States
        compound.addValue( "State", this.state );
        compound.addValue( "NewState", this.newState );

        // Progress
        compound.addValue( "Progress", this.progess );
        compound.addValue( "LastProgress", this.lastProgress );

        // Sticky
        compound.addValue( "Sticky", this.sticky );

        // Blocks
        if ( this.breakBlocks != null ) {
            compound.addValue( "BreakBlocks", this.breakBlocks );
        }

        if ( this.attachedBlocks != null ) {
            compound.addValue( "AttachedBlocks", this.attachedBlocks );
        }
    }

}
