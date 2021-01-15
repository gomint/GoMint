package io.gomint.addons;

import io.gomint.addons.components.ComponentHolder;

/**
 * Interface describing a block's properties and components.
 *
 * Standard Minecraft components are accessible through specialized accessors whereas
 * custom components loaded in through behaviour packs must be queried explicitly by their
 * namespaced names.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability 1
 */
public interface BlockDefinition extends ComponentHolder {

    /**
     * @return The addon that registered this block
     */
    Addon addon();

    /**
     * @return The block's unique name, including its namespace
     */
    String identifier();

    /**
     * @return The amount of light this block will absorb
     */
    int lightAbsorption();

    /**
     * @return The amount of light this block will emit in a range [0.0, 1.0]
     */
    float lightEmission();

    /**
     * @return Whether or not the block will break when pushed by a piston
     */
    boolean breakOnPush();

    /**
     * @return Whether or not the block is breathable
     */
    boolean breathable();

    /**
     * @return The time it takes to destroy this block ; greater values result in greater mining times
     */
    float destroyTime();

    /**
     * @return The block's resistance towards explosions
     */
    float explosionResistance();

    /**
     * @return How likely the block will be destroyed by flames when on fire
     */
    int burnOdds();

    /**
     * @return How likely the block will catch flame when next to a fire
     */
    int flameOdds();

    /**
     * @return The block's friction value applied when walking on this block
     */
    float friction();

    /**
     * @return Whether or not the block is immovable (i.e. cannot be moved by pistons)
     */
    boolean immovable();

    /**
     * @return Whether or not the block can only be pushed by pistons and will not stick to them
     */
    boolean pistonPushOnly();

    /**
     * @return Whether or not this block prevents entities from jumping while standing on it
     */
    boolean preventsJumping();

    /**
     * @return Whether or not this block cannot be walked on
     */
    boolean unwalkable();

}
