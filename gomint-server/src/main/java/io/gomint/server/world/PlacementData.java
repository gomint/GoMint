package io.gomint.server.world;

import io.gomint.server.util.BlockIdentifier;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlacementData {

    private BlockIdentifier blockIdentifier;
    private NBTTagCompound compound;

    public PlacementData(BlockIdentifier blockIdentifier, NBTTagCompound compound) {
        this.blockIdentifier = blockIdentifier;
        this.compound = compound;
    }

    public BlockIdentifier getBlockIdentifier() {
        return blockIdentifier;
    }

    public PlacementData setBlockIdentifier(BlockIdentifier blockIdentifier) {
        this.blockIdentifier = blockIdentifier;
        return this;
    }

    public NBTTagCompound getCompound() {
        return compound;
    }

    public PlacementData setCompound(NBTTagCompound compound) {
        this.compound = compound;
        return this;
    }

}
