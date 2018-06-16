/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.v1_8;

import io.gomint.server.entity.tileentity.*;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.anvil.tileentity.TileEntityConverters;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TileEntities implements TileEntityConverters {

    private static final Logger LOGGER = LoggerFactory.getLogger( TileEntities.class );

    // Individual converters for the tiles
    private SignConverter signConverter;
    private SkullConverter skullConverter;
    private ChestConverter chestConverter;
    private FlowerPotConverter flowerPotConverter;
    private EnchantTableConverter enchantTableConverter;
    private DispenserConverter dispenserConverter;
    private EndPortalConverter endPortalConverter;
    private BeaconConverter beaconConverter;
    private EnderChestConverter enderChestConverter;
    private DaylightDetectorConverter daylightDetectorConverter;
    private BedConverter bedConverter;

    /**
     * Construct 1.8 converter for the given world
     *
     * @param worldAdapter which should be used in all convert operations
     */
    public TileEntities( WorldAdapter worldAdapter ) {
        this.signConverter = new SignConverter( worldAdapter );
        this.skullConverter = new SkullConverter( worldAdapter );
        this.chestConverter = new ChestConverter( worldAdapter );
        this.flowerPotConverter = new FlowerPotConverter( worldAdapter );
        this.enchantTableConverter = new EnchantTableConverter( worldAdapter );
        this.dispenserConverter = new DispenserConverter( worldAdapter );
        this.endPortalConverter = new EndPortalConverter( worldAdapter );
        this.beaconConverter = new BeaconConverter( worldAdapter );
        this.enderChestConverter = new EnderChestConverter( worldAdapter );
        this.daylightDetectorConverter = new DaylightDetectorConverter( worldAdapter );
        this.bedConverter = new BedConverter( worldAdapter );
    }

    @Override
    public TileEntity read( NBTTagCompound compound ) {
        String id = compound.getString( "id", "" );
        switch ( id ) {
            case "DLDetector":
                return this.daylightDetectorConverter.readFrom( compound );
            case "EnderChest":
                return this.enderChestConverter.readFrom( compound );
            case "Beacon":
                return this.beaconConverter.readFrom( compound );
            case "AirPortal":
                return this.endPortalConverter.readFrom( compound );
            case "Trap":
                return this.dispenserConverter.readFrom( compound );
            case "EnchantTable":
                return this.enchantTableConverter.readFrom( compound );
            case "FlowerPot":
                return this.flowerPotConverter.readFrom( compound );
            case "Chest":
                return this.chestConverter.readFrom( compound );
            case "Skull":
                return this.skullConverter.readFrom( compound );
            case "Sign":
                return this.signConverter.readFrom( compound );
            case "Bed":
                return this.bedConverter.readFrom( compound );
            default:
                LOGGER.warn( "Unknown id {} -> {}", id, compound );
                return null;
        }
    }

}
