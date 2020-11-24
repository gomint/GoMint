/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.World;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class WorldBorder {

    private final World world;
    private boolean active = false;
    private double centerX = 0.0D;
    private double centerZ = 0.0D;
    private double size = 60000000.0D;
    private double safeZone = 5.0D;
    private double warningBlocks = 5.0D;
    private double warningTime = 15.0D;
    private double sizeLerpTarget = 60000000.0D;
    private long sizeLerpTime = 0;
    private double damagePerBlock = 0.2D;

    /**
     * Construct a new WorldBorder. It overtakes the settings from a NBTTagCompound.
     *
     * @param world    The world for which this border should be used
     * @param compound The compound from which the settings should be read
     */
    public WorldBorder( World world, NBTTagCompound compound ) {
        this.world = world;
        if ( !compound.containsKey( "BorderCenterX" ) || !compound.containsKey( "BorderCenterZ" ) ) {
            this.active = false;
            return;
        }

        this.active = true;
        this.centerX = compound.getDouble( "BorderCenterX", 0.0D );
        this.centerZ = compound.getDouble( "BorderCenterZ", 0.0D );
        this.size = compound.getDouble( "BorderSize", 60000000.0D );
        this.safeZone = compound.getDouble( "BorderSafeZone", 5.0D );
        this.warningBlocks = compound.getDouble( "BorderWarningBlocks", 5.0D );
        this.warningTime = compound.getDouble( "BorderWarningTime", 15.0D );
        this.sizeLerpTarget = compound.getDouble( "BorderSizeLerpTarget", 60000000.0D );
        this.sizeLerpTime = compound.getLong( "BorderSizeLerpTime", 0L );
        this.damagePerBlock = compound.getDouble( "BorderDamagePerBlock", 0.2D );
    }

    /**
     * Checks whether or not the world border is actually active.
     *
     * @return Whether or not the world boarder is active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Gets the world the world border belongs to.
     *
     * @return The world the world border belongs to
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Gets the center of the world border on the x-axis.
     *
     * @return The center of the world border on the x-axis
     */
    public double getCenterX() {
        return this.centerX;
    }

    /**
     * Gets the center of the world border on the z-axis.
     *
     * @return The center of the world border on the z-axis
     */
    public double getCenterZ() {
        return this.centerZ;
    }

    /**
     * Gets the size of the world border, i.e. how far it extends into both the x- and the z-direction.
     *
     * @return The size of the world border
     */
    public double getSize() {
        return this.size;
    }

    /**
     * Gets the amount of blocks a player may be outside the world border before taking damage.
     *
     * @return The safe-zone radius of the world border
     */
    public double getSafeZone() {
        return this.safeZone;
    }

    /**
     * If the world border is contracting and a player is within a specified radius around the border a warning
     * will be shown to the player (the player's screen will tint red).
     *
     * @return The number of warning blocks of the border
     */
    public double getWarningBlocks() {
        return this.warningBlocks;
    }

    /**
     * If the world border is contracting and a player will be reached by it within this time a warning will be
     * shown (the player's screen will tint red).
     *
     * @return The warning time of the border
     */
    public double getWarningTime() {
        return this.warningTime;
    }

    /**
     * Gets the target size to which the world border to contracting itself.
     *
     * @return The target size of the world border
     */
    public double getSizeLerpTarget() {
        return this.sizeLerpTarget;
    }

    /**
     * Gets the time during which the world border in reach its target size.
     *
     * @return The time in which the border will reach its target size
     */
    public long getSizeLerpTime() {
        return this.sizeLerpTime;
    }

    /**
     * Gets the damage a player takes when outside the world border's safe-zone on a per-block / per-second
     * basis.
     *
     * @return The damage the player should take per block outside the safe-zone per second
     */
    public double getDamagePerBlock() {
        return this.damagePerBlock;
    }

}
