/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity;

import io.gomint.gui.Form;
import io.gomint.gui.FormListener;
import io.gomint.inventory.Inventory;
import io.gomint.inventory.PlayerInventory;
import io.gomint.math.Vector;
import io.gomint.permission.PermissionManager;
import io.gomint.player.PlayerSkin;
import io.gomint.world.Gamemode;
import io.gomint.world.Sound;
import io.gomint.world.SoundData;

import java.util.Locale;
import java.util.UUID;

/**
 * @author BlackyPaw
 * @author Digot
 * @version 1.0
 */
public interface EntityPlayer extends EntityCreature {

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
     * Set the new gamemode for this player
     *
     * @param gamemode The new gamemode to be used
     */
    void setGamemode( Gamemode gamemode );

    /**
     * Get the current gamemode
     *
     * @return The gamemode the player is in
     */
    Gamemode getGamemode();

    /**
     * Is this player op'ed?
     *
     * @return true when op'ed, false when not
     */
    boolean isOp();

    /**
     * Hide another player from this player
     *
     * @param player The player which should be hidden
     */
    void hidePlayer( EntityPlayer player );

    /**
     * Show a hidden player again
     *
     * @param player The player which should be shown again
     */
    void showPlayer( EntityPlayer player );

    /**
     * Check if given player is hidden to this player
     *
     * @param player The player which should be checked for
     * @return true if this player can't see the player given, false it it can
     */
    boolean isHidden( EntityPlayer player );

    /**
     * Get the skin of a player. This is readonly access currently since we figure out how to change the skin.
     *
     * @return skin which the client has sent on login
     */
    PlayerSkin getSkin();

    /**
     * Get the players inventory
     *
     * @return players inventory
     */
    PlayerInventory getInventory();

    /**
     * Opens a inventory for the player
     *
     * @param inventory which should be opened
     */
    void openInventory( Inventory inventory );

    /**
     * Get the unique XBOX live id. Is empty string if not in xbox live mode
     *
     * @return xbox live id or empty string
     */
    String getXboxID();

    /**
     * Send a message to the client, this uses the normal {@link ChatType} enum.
     *
     * @param message which should be send to the client
     */
    void sendMessage( String message );

    /**
     * Send a message with a given type to the client
     *
     * @param message which should be send
     * @param type    of the message
     */
    void sendMessage( ChatType type, String... message );

    /**
     * Check if player has a specific permission
     *
     * @param permission which should be checked for
     * @return true if the player has this permission, false if not
     */
    boolean hasPermission( String permission );

    /**
     * Get the view distance of this player
     *
     * @return radius of chunks this player can see
     */
    int getViewDistance();

    /**
     * Transfer player to another server
     *
     * @param host IP or Hostname for the user to connect to
     * @param port Of the new Server
     */
    void transfer( String host, int port );

    /**
     * Return the network latency
     *
     * @return
     */
    int getPing();

    /**
     * Display new form and get a listener for the response
     *
     * @param form which should be shown
     * @param <T>  type of return value from the response
     * @return form listener to attaching for response
     */
    <T> FormListener<T> showForm( Form form );

    /**
     * Get the players permission manager
     *
     * @return permission manager
     */
    PermissionManager getPermissionManager();

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
     * Is this player still online?
     *
     * @return true if online, false if not
     */
    boolean isOnline();

    /**
     * Locale of this player
     *
     * @return locale of the players client
     */
    Locale getLocale();

    /**
     * Disconnect a player for the given reason
     *
     * @param reason for disconnect
     */
    void disconnect( String reason );

    /**
     * Get absolute amount of xp in this entity
     *
     * @return absolute amount of xp
     */
    int getXP();

    /**
     * Percentage of xp for next level
     *
     * @return percentage of next level
     */
    float getXPPercentage();

    /**
     * Set the amount of xp this entity has
     *
     * @param xp of this entity
     */
    void setXP( int xp );

    /**
     * Get exp level of this entity
     *
     * @return exp level
     */
    int getLevel();

    /**
     * Set the level of the exp bar
     *
     * @param level of this entity
     */
    void setLevel( int level );

    /**
     * Play a sound for this player
     *
     * @param location of the sound in the client
     * @param sound    The sound which should be played
     * @param pitch    The pitch at which the sound should be played
     * @param data     additional data for the sound
     */
    void playSound( Vector location, Sound sound, byte pitch, SoundData data );

    /**
     * Play a sound for this player
     *
     * @param location of the sound in the client
     * @param sound    The sound which should be played
     * @param pitch    The pitch at which the sound should be played
     */
    void playSound( Vector location, Sound sound, byte pitch );

}
