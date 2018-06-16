/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum TileEntities {

    /**
     * Represents a sign. This TileEntity holds 4 lines of strings to be displayed on a piece of wood
     */
    SIGN( "Sign", SignTileEntity.class ),

    /**
     * Represents a chest. This TileEntity holds a inventory which can hold up to 27 itemstacks
     */
    CHEST( "Chest", ChestTileEntity.class ),

    /**
     * Represents a skull. This TileEntity holds rotation and the type of the skull
     */
    SKULL( "Skull", SkullTileEntity.class ),

    /**
     * Represents a noteblock. This TileEntity holds only the note of the block
     */
    NOTEBLOCK( "Music", NoteblockTileEntity.class ),

    /**
     * Represents a ender chest. This TileEntity does not contain any other informations
     */
    ENDER_CHEST( "EnderChest", EnderChestTileEntity.class ),

    /**
     * Represents a flower pot. Contains data about which item it holds
     */
    FLOWER_POT( "FlowerPot", FlowerPotTileEntity.class ),

    /**
     * Represents a command block. Contains data like command string, output etc.
     */
    COMMAND_BLOCK( "CommandBlock", CommandBlockTileEntity.class ),

    /**
     * Represents a item frame. It holds a item and rotation states
     */
    ITEM_FRAME( "ItemFrame", ItemFrameTileEntity.class ),

    /**
     * Enchantment table. Stores nothing except a optional custom name
     */
    ENCHANT_TABLE( "EnchantTable", EnchantTableTileEntity.class ),

    /**
     * Holds nothing :)
     */
    DAYLIGHT_DETECTOR( "DaylightDetector", DaylightDetectorTileEntity.class ),

    /**
     * More or less a cooler chest
     */
    SHULKER_BOX( "ShulkerBox", ShulkerBoxTileEntity.class ),

    /**
     * Data for the piston extension
     */
    PISTON_ARM( "PistonArm", PistonArmTileEntity.class ),

    /**
     * Data for a furnace
     */
    FURNACE( "Furnace", FurnaceTileEntity.class ),

    /**
     * Data for a bed
     */
    BED( "Bed", BedTileEntity.class ),

    /**
     * Data for a dispenser
     */
    DISPENSER( "Dispenser", DispenserTileEntity.class ),

    // DROPPER( "Dropper", )

    /**
     * Data for beacon
     */
    BEACON( "Beacon", BeaconTileEntity.class ),

    /**
     * Data for end portals
     */
    END_PORTAL( "EndPortal", EndPortalTileEntity.class );

    /**
     * Data for banner
     */
    //BANNER( "Banner", BannerTileEntity.class );

    private static final Logger LOGGER = LoggerFactory.getLogger( TileEntities.class );
    private final String nbtID;
    private MethodHandle tileEntityConstructor;

    /**
     * Construct a new TileEntity enum value
     *
     * @param nbtID           The ID which can be found in NBT Tags
     * @param tileEntityClass The class which should be used to instantiate the TileEntity
     */
    TileEntities( String nbtID, Class<? extends TileEntity> tileEntityClass ) {
        this.nbtID = nbtID;

        try {
            this.tileEntityConstructor = MethodHandles.lookup().unreflectConstructor( tileEntityClass.getConstructor( NBTTagCompound.class, WorldAdapter.class ) );
        } catch ( IllegalAccessException | NoSuchMethodException e ) {
            e.printStackTrace();
            this.tileEntityConstructor = null;
        }
    }

    /**
     * Construct a new TileEntity which then reads in the data from the given Compound
     *
     * @param compound The compound from which the data should be read
     * @param world    The world in which this TileEntity resides
     * @return The constructed and ready to use TileEntity or null
     */
    public static TileEntity construct( NBTTagCompound compound, WorldAdapter world ) {
        // Check if compound has a id
        String id = compound.getString( "id", null );
        if ( id == null ) {
            return null;
        }

        // Search for correct tile entity
        for ( TileEntities tileEntities : values() ) {
            if ( tileEntities.nbtID.equals( id ) ) {
                try {
                    return (TileEntity) tileEntities.tileEntityConstructor.invoke( compound, world );
                } catch ( Throwable throwable ) {
                    LOGGER.warn( "Could not build up tile entity: ", throwable );
                    return null;
                }

            }
        }

        LOGGER.warn( "Unknown tile entity found: {} -> {}", id, compound );
        return null;
    }

}
