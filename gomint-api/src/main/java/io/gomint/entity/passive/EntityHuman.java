/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityCreature;
import io.gomint.inventory.PlayerInventory;
import io.gomint.player.PlayerSkin;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EntityHuman extends EntityCreature {

    /**
     * Create a new entity human with no config
     *
     * @return empty, fresh human
     */
    static EntityHuman create() {
        return GoMint.instance().createEntity( EntityHuman.class );
    }

    /**
     * Gets the name of the player. It is NOT globally unique since the
     * player can change it inside the Client. Yet it is unique for all
     * players on the same server.
     *
     * @return The name player's name
     */
    String getName();

    /**
     * Gets the player's UUID. It has yet to be researched how unique this
     * one is as it may be specified by the player during the login sequence.
     *
     * @return The player's UUID.
     */
    UUID getUUID();

    /**
     * Get the skin of a player. This is readonly access currently since we figure out how to change the skin.
     *
     * @return skin which the client has sent on login
     */
    PlayerSkin getSkin();

    /**
     * Set the skin of this human
     *
     * @param skin which should be set
     */
    void setSkin( PlayerSkin skin );

    /**
     * Get the name which is listed in the tablist (displayName)
     *
     * @return display name
     */
    String getDisplayName();

    /**
     * Set a new display name
     *
     * @param displayName which should be used
     */
    void setDisplayName( String displayName );

    /**
     * Get the unique XBOX live id. Is empty string if not in xbox live mode
     *
     * @return xbox live id or empty string
     */
    String getXboxID();

    /**
     * Get the players inventory
     *
     * @return players inventory
     */
    PlayerInventory getInventory();

    /**
     * Set hunger level
     *
     * @param amount of hunger
     */
    void setHunger( float amount );

    /**
     * Get the hunger level
     *
     * @return hunger level
     */
    float getHunger();

    /**
     * Set saturation level
     *
     * @param amount of saturation
     */
    void setSaturation( float amount );

    /**
     * Get the saturation level
     *
     * @return saturation level
     */
    float getSaturation();

    /**
     * Set player sneaking or not
     *
     * @param value true for sneaking, false for not sneaking
     */
    void setSneaking( boolean value );

    /**
     * Is this player sneaking?
     *
     * @return true when sneaking, false when not
     */
    boolean isSneaking();

    /**
     * Set player sprinting or not
     *
     * @param value true for sprinting, false for not sprinting
     */
    void setSprinting( boolean value );

    /**
     * Check if entity is sprinting
     *
     * @return true when sprinting, false when not
     */
    boolean isSprinting();

}
