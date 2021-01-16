package io.gomint.server.addons;

import com.jsoniter.JsonIterator;
import io.gomint.server.blocks.BlockCatalogue;
import io.gomint.server.blocks.BlockDefinition;
import io.gomint.server.blocks.BlockDefinitionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * An interface for loading an addon and registering its components.
 * <p>
 * Different types of modules must be loaded in different ways. It is therefore required
 * that a suitable loading mechanism is employed when loading modules.
 *
 * @author Ciel DeVille
 * @version 1.0
 */
class AddonLoader {

    /*
     * Catalogues to register definitions with.
     */
    private final BlockCatalogue blockCatalogue;

    private final Logger logger = LoggerFactory.getLogger(AddonLoader.class);

    AddonLoader(BlockCatalogue blockCatalogue) {
        this.blockCatalogue = blockCatalogue;
    }

    /**
     * Attempts to load the addon module into memory.
     *
     * @param addon The addon to be loaded
     * @throws IOException Thrown if loading the addon failed
     */
    public boolean load(Addon addon) throws IOException {
        AddonContext context = addon.context();

        try {
            context
                    .entries()
                    .forEach(entry -> {
                        try {
                            this.loadEntry(addon, context, entry);
                        } catch (IOException e) {
                            // TODO: Decide if this exception should escalate and produce false as return
                            this.logger.error("Failed to load entry '{}'", entry, e);
                        }
                    });
        } catch (IOException e) {
            throw new IOException("Could not retrieve list of entries", e);
        }

        return true;
    }

    /**
     * If the addon is currently loaded into memory, unloads all resources previously loaded.
     *
     * @param addon The addon to be unloaded
     * @throws IOException Thrown if unloading the addon failed
     */
    public void unload(Addon addon) throws IOException {
        this.blockCatalogue.removeDefinitionsOfAddon(addon);
    }


    // ================================= Loading

    private void loadEntry(Addon addon, AddonContext context, String entry) throws IOException {
        switch (entry) {
            case "blocks.json":
                this.parseBlockDefinitionList(addon, context, entry);
                break;
            case "sounds/music_definitions.json":
            case "sounds/sound_definitions.json":
                // TODO: Implement loading of sounds
//                                this.loadSoundDefinitionList(addon, context, entry);
                break;
            default:
                // TODO: Implement loading of translation strings
//                                if (entry.startsWith("texts/")) {
//                                    this.loadTranslationStrings(addon, context, entry);
//                                }

                // TODO: Implement loading of items
//                                if (entry.startsWith("items/")) {
//                                    this.loadItemDefinition(addon, context, entry);
//                                }

                if (entry.startsWith("blocks/")) {
                    this.parseBlockDefinitionFile(addon, context, entry);
                }
                break;
        }
    }

    private JsonIterator openJsonEntry(AddonContext context, String entry) throws IOException {
        return JsonIterator.parse(context.openEntry(entry), 4096);
    }

    private void parseBlockDefinitionFile(Addon addon, AddonContext context, String entry) throws IOException {
        try (JsonIterator json = this.openJsonEntry(context, entry)) {
            for (String attrib = json.readObject(); attrib != null; attrib = json.readObject()) {
                if ("minecraft:block".equals(attrib)) {
                    this.parseBlockDefinition(addon, json);
                } else {
                    json.skip();
                }
            }
        }
    }

    private void parseBlockDefinition(Addon addon, JsonIterator json) throws IOException {
        BlockDefinitionBuilder builder = this.blockCatalogue.defineBlock(addon);

        for (String attrib = json.readObject(); attrib != null; attrib = json.readObject()) {
            switch (attrib) {
                case "description":
                    this.parseBlockDefinitionDescription(builder, json);
                    break;
                case "components":
                    this.parseBlockDefinitionComponents(builder, json);
                    break;
                default:
                    json.skip();
                    break;
            }
        }

        try {
            builder.createDefinition();
        } catch (IllegalArgumentException e) {
            this.logger.error("Failed to define block: illegal identifier '{}'", builder.identifier());
        }
    }

    private void parseBlockDefinitionDescription(BlockDefinitionBuilder builder, JsonIterator json) throws IOException {
        for (String property = json.readObject(); property != null; property = json.readObject()) {
            switch (property) {
                case "identifier":
                    builder.identifier(json.readString());
                    break;
                default:
                    json.skip();
                    break;
            }
        }
    }

    private void parseBlockDefinitionComponents(BlockDefinitionBuilder builder, JsonIterator json) throws IOException {
        for (String component = json.readObject(); component != null; component = json.readObject()) {
            switch (component) {
                /*
                 * Official Mojang components
                 *
                 * Please note that not all components are read by GoMint since not all are necessarily relevant
                 * to server functionality.
                 */
                case "minecraft:block_light_absorption":
                    builder.lightAbsorption(json.readInt());
                    break;
                case "minecraft:block_light_emission":
                    builder.lightEmission(json.readFloat());
                    break;
                case "minecraft:breakonpush":
                    builder.breakOnPush(json.readBoolean());
                    break;
                case "minecraft:breathability":
                    builder.breathable(!json.readString().equalsIgnoreCase("solid"));
                    break;
                case "minecraft:destroy_time":
                    builder.destroyTime(json.readFloat());
                    break;
                case "minecraft:explosion_resistance":
                    builder.explosionResistance(json.readFloat());
                    break;
                case "minecraft:flammable":
                    for (String attrib = json.readObject(); attrib != null; attrib = json.readObject()) {
                        switch (attrib) {
                            case "burn_odds":
                                builder.burnOdds(json.readInt());
                                break;
                            case "flame_odds":
                                builder.flameOdds(json.readInt());
                                break;
                        }
                    }
                    break;
                case "minecraft:friction":
                    builder.friction(json.readFloat());
                    break;
                case "minecraft:immovable":
                    builder.immovable(json.readBoolean());
                    break;
                case "minecraft:onlypistonpush":
                    builder.pistonPushOnly(json.readBoolean());
                    break;
                case "minecraft:preventsjumping":
                    builder.preventsJumping(json.readBoolean());
                    break;
                case "minecraft:unwalkable":
                    builder.unwalkable(json.readBoolean());
                    break;

                /*
                 * Undocumented Mojang components
                 *
                 * This section serves only as a list for future reference
                 */
//                case "minecraft:loot":
//                    String lootTableEntry = json.readString();
//                    break;

                /*
                 * Reserved GoMint components
                 */
                case "gomint:block_behaviours":
                    // TODO: Implement block behaviours
                    json.skip();
                    break;

                /*
                 * Custom components
                 */
                default:
                    // Possibly a custom component:
                    // TODO: Implement component registry
                    json.skip();
                    break;
            }
        }
    }

    private void parseBlockDefinitionList(Addon addon, AddonContext context, String entry) throws IOException {
        try (JsonIterator json = this.openJsonEntry(context, entry)) {
            for (String identifier = json.readObject(); identifier != null; identifier = json.readObject()) {
                if (!identifier.equals("format_version")) {
                    // This is a block declaration
                    try {
                        this.blockCatalogue.defineBlock(addon).identifier(identifier).createDefinition();
                    } catch (IllegalArgumentException e) {
                        this.logger.info("Failed to define block: illegal identifier '{}'", identifier);
                    }
                }
                json.skip();
            }
        }
    }

}
