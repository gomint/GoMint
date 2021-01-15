package io.gomint.server.addons;

import io.gomint.addons.components.Component;

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
     * The block's unique name including its namespace
     */
    private final String name;


    /*
     * Properties of Mojang-defined block components
     *
     * Whereever no default value was specified within Mojang's addon documentation,
     * sensible defaults were guessed. To make these guesses, it was assumed that the
     * "typical" block was akin to stone.
     */

    // Default taken from addon documentation
    private int lightAbsorption = 0;
    // Default taken from addon documentation
    private float lightEmission = 0.0F;
    // Educated guess, see above
    private boolean breakOnPush = false;
    // Default taken from addon documentation
    private boolean breathable = false;
    // Default taken from addon documentation
    private float destroyTime = 0.0F;
    // Default taken from addon documentation
    private float explosionResistance = 0.0F;
    // Default taken from addon documentation
    private int burnOdds = 0;
    // Default taken from addon documentation
    private int flameOdds = 0;
    // Default taken from addon documentation
    private float friction = 0.1F;
    // Educated guess, see above
    private boolean immovable = false;
    // Educated guess, see above
    private boolean pistonPushOnly = false;
    // Educated guess, see above
    private boolean preventsJumping = false;
    // Educated guess, see above
    private boolean unwalkable = false;

    /*
     * Custom components
     *
     * Additional components added to blocks can be stored here
     */

    private Map<Class<? extends Component>, Component> additionalComponents;

    public BlockDefinition(String name) {
        this.name = name;
        this.additionalComponents = new HashMap<>();
    }


    @Override
    public String name() {
        return null;
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
