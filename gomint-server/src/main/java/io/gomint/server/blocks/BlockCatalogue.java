package io.gomint.server.blocks;

import io.gomint.server.addons.Addon;

import java.util.*;

/**
 * A catalog containing all known blocks.
 * <p>
 * This catalog holds all definitions of blocks currently defined through vanilla
 * Minecraft or installed addons. It provides read-only access to these definitions
 * and handles generation of runtime network IDs.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class BlockCatalogue {

    private Map<String, BlockDefinition> definitions;
    private Map<UUID, List<String>> definitionsPerAddon;

    /**
     * Constructs an empty block catalogue.
     */
    public BlockCatalogue() {
        this.definitions = new HashMap<>();
        this.definitionsPerAddon = new HashMap<>();
    }

    /**
     * Begins the process of defining a new block for this catalogue.
     *
     * @param addon The addon that is defining the block
     * @return A block definition builder to be used to define the block
     */
    public BlockDefinitionBuilder defineBlock(Addon addon) {
        return new BlockDefinitionBuilder(this, addon);
    }

    /**
     * Removes all block definitions created by the specified addon.
     *
     * @param addon The addon whose definitions should be removed
     */
    public void removeDefinitionsOfAddon(Addon addon) {
        List<String> identifiers = this.definitionsPerAddon.get(addon.uuid());
        if (identifiers == null) {
            return;
        }

        for (String identifier : identifiers) {
            this.definitions.remove(identifier);
        }
        this.definitionsPerAddon.remove(addon.uuid());
    }

    /**
     * Registers a new block definition with the catalogue. This is invoked by a BlockDefinitionBuilder upon
     * creation of the definition.
     *
     * @param identifier The block's unique identifier
     */
    BlockDefinition registerDefinition(Addon addon, String identifier) {
        BlockDefinition definition = this.definitions.get(identifier);
        if (definition == null) {
            definition = new BlockDefinition(addon, identifier);
            this.definitions.put(identifier, definition);
        }

        return definition;
    }

    /**
     * Adds a definition to the catalogue.
     *
     * @param definition The definition to add
     */
    private void addDefinition(BlockDefinition definition) {
        this.definitions.put(definition.identifier(), definition);

        List<String> addonDefinitions = this.definitionsPerAddon.computeIfAbsent(definition.addon().uuid(), k -> new ArrayList<>());
        addonDefinitions.add(definition.identifier());
    }

}
