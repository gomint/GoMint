/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.StringRegistry;
import io.gomint.server.util.ClassPath;
import io.gomint.server.world.block.Banner;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TileEntities {

    private static final Logger LOGGER = LoggerFactory.getLogger(TileEntities.class);
    private final StringRegistry<TileEntity> generators;
    private final Items items;

    public TileEntities(ClassPath classPath, Items items) {
        this.items = items;
        this.generators = new StringRegistry<>( classPath, (clazz, id) -> {
            return null;
        } );

        this.generators.register("io.gomint.server.entity.tileentity");
        Registry.register(this.generators);
    }

    /**
     * Re
    SKULL("Skull", SkullTileEntity.class),

    /**
     * Represents a noteblock. This TileEntity holds only the note of the block
     
    NOTEBLOCK("Music", NoteblockTileEntity.class),

    /**
     * Represents a ender chest. This TileEntity does not contain any other informations
     
    ENDER_CHEST("EnderChest", EnderChestTileEntity.class),

    /**
     * Represents a flower pot. Contains data about which item it holds
     
    FLOWER_POT("FlowerPot", FlowerPotTileEntity.class),

    /**
     * Represents a command block. Contains data like command string, output etc.
     
    COMMAND_BLOCK("CommandBlock", CommandBlockTileEntity.class),

    /**
     * Represents a item frame. It holds a item and rotation states
     
    ITEM_FRAME("ItemFrame", ItemFrameTileEntity.class),

    /**
     * Enchantment table. Stores nothing except a optional custom name
     
    ENCHANT_TABLE("EnchantTable", EnchantTableTileEntity.class),

    /**
     * Holds nothing :)
    
    DAYLIGHT_DETECTOR("DaylightDetector", DaylightDetectorTileEntity.class),

    /**
     * More or less a cooler chest
    
    SHULKER_BOX("ShulkerBox", ShulkerBoxTileEntity.class),

    /**
     * Data for the piston extension
    
    PISTON_ARM("PistonArm", PistonArmTileEntity.class),

    /**
     * Data for a furnace
    
    FURNACE("Furnace", FurnaceTileEntity.class),

    /**
     * Data for a bed
    
    BED("Bed", BedTileEntity.class),

    /**
     * Data for a dispenser
    
    DISPENSER("Dispenser", DispenserTileEntity.class),

    /**
     * Data for beacon
    
    BEACON("Beacon", BeaconTileEntity.class),

    /**
     * Data for end portals
    
    END_PORTAL("EndPortal", EndPortalTileEntity.class),

    /**
     * Data for banner
    
    BANNER("Banner", BannerTileEntity.class),

    /**
     * Data for mob spawner
    
    MOB_SPAWNER("MobSpawner", MobSpawnerTileEntity.class),

    /**
     * Data for a jukebox
    
    JUKEBOX("Jukebox", JukeboxTileEntity.class),

    /**
     * Data for a hopper
    
    HOPPER("Hopper", HopperTileEntity.class),

    /**
     * Data for a comparator
    
    COMPARATOR("Comparator", ComparatorTileEntity.class),

    /**
     * Data for a cauldron
    
    CAULDRON("Cauldron", CauldronTileEntity.class),

    /**
     * Data for a dropper
    
    DROPPER("Dropper", DropperTileEntity.class),

    BEE_HIVE("Beehive", BeehiveTileEntity.class),

    SMOKER("Smoker", SmokerTileEntity.class);*/

    /**
     * Construct a new TileEntity which then reads in the data from the given Compound
     *
     * @param compound The compound from which the data should be read
     * @param block    The block in which this TileEntity resides
     * @return The constructed and ready to use TileEntity or null
     */
    public TileEntity construct(NBTTagCompound compound, Block block) {
        // Check if compound has a id
        String id = compound.getString("id", null);
        if (id == null) {
            return null;
        }

        Generator<TileEntity> tileEntityGenerator = this.generators.getGenerator(id, 2);
        if (tileEntityGenerator != null) {
            TileEntity tileEntity = tileEntityGenerator.generate(block, this.items);
            tileEntity.fromCompound(compound);
            return tileEntity;
        }

        LOGGER.warn("Unknown tile entity found: {} -> {}", id, compound);
        return null;
    }

    public TileEntity construct(Class<? extends TileEntity> teClass, NBTTagCompound compound, Object ... init) {
        Generator<TileEntity> tileEntityGenerator = this.generators.getGenerator(teClass, init.length);
        if (tileEntityGenerator != null) {
            TileEntity tileEntity = tileEntityGenerator.generate(init);
            tileEntity.fromCompound(compound);
            return tileEntity;
        }

        return null;
    }

}
