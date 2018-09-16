/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.converter.anvil.tileentity.v1_8;

import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.world.converter.anvil.tileentity.TileEntityConverters;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
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
    private FurnaceConverter furnaceConverter;
    private ControlConverter controlConverter;
    private NoteblockConverter noteblockConverter;
    private HopperConverter hopperConverter;
    private RecordPlayerConverter recordPlayerConverter;
    private BannerConverter bannerConverter;

    /**
     * Construct 1.8 converter for the given world
     *
     * @param items        which can be used by this converter
     * @param itemConverter mappings for string -> int ids
     */
    public TileEntities( Items items, Object2IntMap<String> itemConverter ) {
        this.signConverter = new SignConverter( items, itemConverter );
        this.skullConverter = new SkullConverter( items, itemConverter );
        this.chestConverter = new ChestConverter( items, itemConverter );
        this.flowerPotConverter = new FlowerPotConverter( items, itemConverter );
        this.enchantTableConverter = new EnchantTableConverter( items, itemConverter );
        this.dispenserConverter = new DispenserConverter( items, itemConverter );
        this.endPortalConverter = new EndPortalConverter( items, itemConverter );
        this.beaconConverter = new BeaconConverter( items, itemConverter );
        this.enderChestConverter = new EnderChestConverter( items, itemConverter );
        this.daylightDetectorConverter = new DaylightDetectorConverter( items, itemConverter );
        this.bedConverter = new BedConverter( items, itemConverter );
        this.furnaceConverter = new FurnaceConverter( items, itemConverter );
        this.controlConverter = new ControlConverter( items, itemConverter );
        this.noteblockConverter = new NoteblockConverter( items, itemConverter );
        this.hopperConverter = new HopperConverter( items, itemConverter );
        this.recordPlayerConverter = new RecordPlayerConverter( items, itemConverter );
        this.bannerConverter = new BannerConverter( items, itemConverter );
    }

    @Override
    public TileEntity read( NBTTagCompound compound ) {
        String id = compound.getString( "id", "" );
        switch ( id ) {
            case "Banner":
                return this.bannerConverter.readFrom( compound );
            case "RecordPlayer":
                return this.recordPlayerConverter.readFrom( compound );
            case "Hopper":
                return this.hopperConverter.readFrom( compound );
            case "Music":
                return this.noteblockConverter.readFrom( compound );
            case "Control":
                return this.controlConverter.readFrom( compound );
            case "Furnace":
                return this.furnaceConverter.readFrom( compound );
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
