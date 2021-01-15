package io.gomint.server.blocks;

import io.gomint.addons.components.Component;
import io.gomint.server.addons.Addon;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of a highly-configurable block definition.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability 1
 */
public class BlockDefinition implements io.gomint.addons.BlockDefinition {

    /*
     * General block properties
     */

    /**
     * The addon that registered this block
     */
    private final Addon addon;
    /**
     * The block's unique name including its namespace
     */
    private final String identifier;


    /*
     * Properties of Mojang-defined block components
     *
     * Whereever no default value was specified within Mojang's addon documentation,
     * sensible defaults were guessed. To make these guesses, it was assumed that the
     * "typical" block was akin to stone.
     */

    // Default taken from addon documentation
    int lightAbsorption = 0;
    // Default taken from addon documentation
    float lightEmission = 0.0F;
    // Educated guess, see above
    boolean breakOnPush = false;
    // Default taken from addon documentation
    boolean breathable = false;
    // Default taken from addon documentation
    float destroyTime = 0.0F;
    // Default taken from addon documentation
    float explosionResistance = 0.0F;
    // Default taken from addon documentation
    int burnOdds = 0;
    // Default taken from addon documentation
    int flameOdds = 0;
    // Default taken from addon documentation
    float friction = 0.1F;
    // Educated guess, see above
    boolean immovable = false;
    // Educated guess, see above
    boolean pistonPushOnly = false;
    // Educated guess, see above
    boolean preventsJumping = false;
    // Educated guess, see above
    boolean unwalkable = false;

    /*
     * Custom components
     *
     * Additional components added to blocks can be stored here
     */

    private Map<Class<? extends Component>, Component> additionalComponents;

    public BlockDefinition(Addon addon, String identifier) {
        this.addon = addon;
        this.identifier = identifier;
        this.additionalComponents = new HashMap<>();
    }


    @Override
    public Addon addon() {
        return this.addon;
    }

    @Override
    public String identifier() {
        return this.identifier;
    }

    @Override
    public int lightAbsorption() {
        return this.lightAbsorption;
    }

    @Override
    public float lightEmission() {
        return this.lightEmission;
    }

    @Override
    public boolean breakOnPush() {
        return this.breakOnPush;
    }

    @Override
    public boolean breathable() {
        return this.breathable;
    }

    @Override
    public float destroyTime() {
        return this.destroyTime;
    }

    @Override
    public float explosionResistance() {
        return this.explosionResistance;
    }

    @Override
    public int burnOdds() {
        return this.burnOdds;
    }

    @Override
    public int flameOdds() {
        return this.flameOdds;
    }

    @Override
    public float friction() {
        return this.friction;
    }

    @Override
    public boolean immovable() {
        return this.immovable;
    }

    @Override
    public boolean pistonPushOnly() {
        return this.pistonPushOnly;
    }

    @Override
    public boolean preventsJumping() {
        return this.preventsJumping;
    }

    @Override
    public boolean unwalkable() {
        return this.unwalkable;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Component> C getComponent(Class<C> cla) {
        return (C) this.additionalComponents.get(cla);
    }
}
