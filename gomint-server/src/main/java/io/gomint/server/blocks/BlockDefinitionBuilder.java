package io.gomint.server.blocks;

import io.gomint.server.addons.Addon;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * A builder class which allows for creating block definitions fluently.
 * definition builders.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class BlockDefinitionBuilder {

    private final BlockCatalogue catalogue;

    private Addon addon;
    private String identifier;
    private OptionalInt lightAbsorption = OptionalInt.empty();
    private Optional<Float> lightEmission = Optional.empty();
    private Optional<Boolean> breakOnPush = Optional.empty();
    private Optional<Boolean> breathable = Optional.empty();
    private Optional<Float> destroyTime = Optional.empty();
    private Optional<Float> explosionResistance = Optional.empty();
    private OptionalInt burnOdds = OptionalInt.empty();
    private OptionalInt flameOdds = OptionalInt.empty();
    private Optional<Float> friction = Optional.empty();
    private Optional<Boolean> immovable = Optional.empty();
    private Optional<Boolean> pistonPushOnly = Optional.empty();
    private Optional<Boolean> preventsJumping = Optional.empty();
    private Optional<Boolean> unwalkable = Optional.empty();

    /**
     * Constructs a new definition builder for a new block definition created by the given addon
     * for a block with the specified unique identifier.
     *
     * @param catalogue The catalogue to register the definition with once created
     * @param addon The addon creating the definition
     */
    BlockDefinitionBuilder(BlockCatalogue catalogue, Addon addon) {
        this.catalogue = catalogue;
        this.addon = addon;
    }

    /**
     * @param identifier The block's unique identifier
     * @return The definition builder
     * @throws IllegalArgumentException Thrown if the identifier is null
     */
    public BlockDefinitionBuilder identifier(String identifier) {
        this.identifier = this.qualifyIdentifier(identifier);
        return this;
    }

    /**
     * @return The identifier of the block currently being defined
     */
    public String identifier() {
        return this.identifier;
    }

    /**
     * @param absorption The block's light absorption value
     * @return The definition builder
     */
    public BlockDefinitionBuilder lightAbsorption(int absorption) {
        this.lightAbsorption = OptionalInt.of(absorption);
        return this;
    }

    /**
     * @param emission The block's light emission value
     * @return The definition builder
     */
    public BlockDefinitionBuilder lightEmission(float emission) {
        this.lightEmission = Optional.of(emission);
        return this;
    }

    /**
     * @param breakOnPush Whether the block will break when pushed by pistons
     * @return The definition builder
     */
    public BlockDefinitionBuilder breakOnPush(boolean breakOnPush) {
        this.breakOnPush = Optional.of(breakOnPush);
        return this;
    }

    /**
     * @param breathable Whether the player can breathe within the block
     * @return The definition builder
     */
    public BlockDefinitionBuilder breathable(boolean breathable) {
        this.breathable = Optional.of(breathable);
        return this;
    }

    /**
     * @param destroyTime The time it takes to destroy the block
     * @return The definition builder
     */
    public BlockDefinitionBuilder destroyTime(float destroyTime) {
        this.destroyTime = Optional.of(destroyTime);
        return this;
    }

    /**
     * @param explosionResistance The block's resistance to explosions
     * @return The definition builder
     */
    public BlockDefinitionBuilder explosionResistance(float explosionResistance) {
        this.explosionResistance = Optional.of(explosionResistance);
        return this;
    }

    /**
     * @param odds The block's chance to burn when on fire
     * @return The definition builder
     */
    public BlockDefinitionBuilder burnOdds(int odds) {
        this.burnOdds = OptionalInt.of(odds);
        return this;
    }

    /**
     * @param odds The block's chance to catch flame when next to a burning block
     * @return The definition builder
     */
    public BlockDefinitionBuilder flameOdds(int odds) {
        this.flameOdds = OptionalInt.of(odds);
        return this;
    }

    /**
     * @param friction The block's friction value
     * @return The definition builder
     */
    public BlockDefinitionBuilder friction(float friction) {
        this.friction = Optional.of(friction);
        return this;
    }

    /**
     * @param immovable Whether the block is immovable to pistons
     * @return The definition builder
     */
    public BlockDefinitionBuilder immovable(boolean immovable) {
        this.immovable = Optional.of(immovable);
        return this;
    }

    /**
     * @param flag Whether the block may only be pushed by pistons and will not stick to them
     * @return The definition builder
     */
    public BlockDefinitionBuilder pistonPushOnly(boolean flag) {
        this.pistonPushOnly = Optional.of(flag);
        return this;
    }

    /**
     * @param flag Whether the block prevents entities from jumping while standing on it
     * @return The definition builder
     */
    public BlockDefinitionBuilder preventsJumping(boolean flag) {
        this.preventsJumping = Optional.of(flag);
        return this;
    }

    /**
     * @param flag Whether the block is unwalkable to entities
     * @return The definition builder
     */
    public BlockDefinitionBuilder unwalkable(boolean flag) {
        this.unwalkable = Optional.of(flag);
        return this;
    }

    /**
     * Creates the block definition or applies all changes to existing block definitions.
     *
     * @return The created definition
     * @throws IllegalArgumentException Thrown if obligatory parameters are missing or invalid, e.g. the block's identifier
     */
    public BlockDefinition createDefinition() {
        this.validateIdentifier(this.identifier);

        BlockDefinition definition = this.catalogue.registerDefinition(this.addon, this.identifier);

        this.lightAbsorption.ifPresent(v -> definition.lightAbsorption = v);
        this.lightEmission.ifPresent(v -> definition.lightEmission = v);
        this.breakOnPush.ifPresent(v -> definition.breakOnPush = v);
        this.breathable.ifPresent(v -> definition.breathable = v);
        this.destroyTime.ifPresent(v -> definition.destroyTime = v);
        this.explosionResistance.ifPresent(v -> definition.explosionResistance = v);
        this.burnOdds.ifPresent(v -> definition.burnOdds = v);
        this.flameOdds.ifPresent(v -> definition.flameOdds = v);
        this.friction.ifPresent(v -> definition.lightEmission = v);
        this.immovable.ifPresent(v -> definition.immovable = v);
        this.pistonPushOnly.ifPresent(v -> definition.pistonPushOnly = v);
        this.preventsJumping.ifPresent(v -> definition.preventsJumping = v);
        this.unwalkable.ifPresent(v -> definition.unwalkable = v);

        return definition;
    }

    /**
     * Qualifies a block identifier (i.e. ensures it has a namespace and a name).
     *
     * @param identifier The block's identifier
     * @return The qualified identifier
     * @throws IllegalArgumentException Thrown if identifier is null
     */
    private String qualifyIdentifier(String identifier) {
        if (identifier == null) throw new IllegalArgumentException("identifier must not be null");
        int index = identifier.lastIndexOf(':');
        if (index == -1) return "minecraft:" + identifier;
        else return identifier;
    }

    /**
     * Validates a block identifier, i.e. ensures it has both a namespace and a name part.
     *
     * @param identifier The identifier to validate.
     */
    private void validateIdentifier(String identifier) {
        if (identifier == null) throw new IllegalArgumentException("identifier must not be null");
        int index = identifier.lastIndexOf(':');
        if (index == -1 || index == 0) throw new IllegalArgumentException("identifier is missing a namespace");
        if (index == identifier.length() - 1) throw new IllegalArgumentException("identifier is missing a name");
    }

}
