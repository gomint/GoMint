/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
 * @stability 3
 */
public interface EntityHuman<E> extends EntityCreature<E> {

    /**
     * Create a new entity human with no config
     *
     * @return empty, fresh human
     */
    static EntityHuman<?> create() {
        return GoMint.instance().createEntity( EntityHuman.class );
    }

    /**
     * Gets the name of the player. It is NOT globally unique since the
     * player can change it inside the Client. Yet it is unique for all
     * players on the same server.
     *
     * @return The name player's name
     */
    String name();

    /**
     * Gets the player's UUID. It has yet to be researched how unique this
     * one is as it may be specified by the player during the login sequence.
     *
     * @return The player's UUID.
     */
    UUID uuid();

    /**
     * Get the skin of a player. This is readonly access currently since we figure out how to change the skin.
     *
     * @return skin which the client has sent on login
     */
    PlayerSkin skin();

    /**
     * Set the skin of this human
     *
     * @param skin which should be set
     * @return entity for chaining
     */
    E skin(PlayerSkin skin );

    /**
     * Get the name which is listed in the tablist (displayName)
     *
     * @return display name
     */
    String displayName();

    /**
     * Set a new display name
     *
     * @param displayName which should be used
     * @return entity for chaining
     */
    E displayName(String displayName );

    /**
     * Get the unique XBOX live id. Is empty string if not in xbox live mode
     *
     * @return xbox live id or empty string
     */
    String xboxID();

    /**
     * Get the players inventory
     *
     * @return players inventory
     */
    PlayerInventory inventory();

    /**
     * Set hunger level
     *
     * @param amount of hunger
     * @return entity for chaining
     */
    E hunger(float amount );

    /**
     * Get the hunger level
     *
     * @return hunger level
     */
    float hunger();

    /**
     * Set saturation level
     *
     * @param amount of saturation
     * @return entity for chaining
     */
    E saturation(float amount );

    /**
     * Get the saturation level
     *
     * @return saturation level
     */
    float saturation();

    /**
     * Set player sneaking or not
     *
     * @param value true for sneaking, false for not sneaking
     * @return entity for chaining
     */
    E sneaking(boolean value );

    /**
     * Is this player sneaking?
     *
     * @return true when sneaking, false when not
     */
    boolean sneaking();

    /**
     * Set player sprinting or not
     *
     * @param value true for sprinting, false for not sprinting
     * @return entity for chaining
     */
    E sprinting(boolean value );

    /**
     * Check if entity is sprinting
     *
     * @return true when sprinting, false when not
     */
    boolean sprinting();

    /**
     * Set player swimming or not
     *
     * @param value true for swimming, false for not swimming
     * @return entity for chaining
     */
    E swimming(boolean value );

    /**
     * Check if entity is swimming
     *
     * @return true when swimming, false when not
     */
    boolean swimming();

    /**
     * Set player spinning or not
     *
     * @param value true for spinning, false for not spinning
     * @return entity for chaining
     */
    E spinning(boolean value );

    /**
     * Check if entity is spinning
     *
     * @return true when spinning, false when not
     */
    boolean spinning();

    /**
     * Get the current player list name
     *
     * @return entry of the player list
     */
    String playerListName();

    /**
     * Set a new player list name. This auto updates for players.
     *
     * @param newPlayerListName new player list name
     * @return entity for chaining
     */
    E playerListName(String newPlayerListName );

    /**
     * Get the exhaustion level
     *
     * @return exhaustion level
     */
    float exhaustion();

    /**
     * Set exhaustion level
     *
     * @param amount of exhaustion
     */
    E exhaustion(float amount);

}
