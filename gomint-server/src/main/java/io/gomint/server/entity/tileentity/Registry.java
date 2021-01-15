package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.plugin.EventCaller;
import io.gomint.server.world.block.Block;
import io.gomint.server.registry.StringRegistry;

public class Registry {

  public static void register(StringRegistry<TileEntity> registry) {
    registry.registerAdditionalConstructor("Banner", 2, in -> new BannerTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Barrel", 2, in -> new BarrelTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Beacon", 2, in -> new BeaconTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Bed", 2, in -> new BedTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Beehive", 2, in -> new BeehiveTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("BrewingStand", 2, in -> new BrewingStandTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Cauldron", 2, in -> new CauldronTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Chest", 2, in -> new ChestTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("CommandBlock", 2, in -> new CommandBlockTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Comparator", 2, in -> new ComparatorTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("DaylightDetector", 2, in -> new DaylightDetectorTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Dispenser", 2, in -> new DispenserTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Dropper", 2, in -> new DropperTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("EnchantTable", 2, in -> new EnchantTableTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("EnderChest", 2, in -> new EnderChestTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("EndPortal", 2, in -> new EndPortalTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("FlowerPot", 2, in -> new FlowerPotTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Furnace", 2, in -> new FurnaceTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Hopper", 2, in -> new HopperTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("ItemFrame", 2, in -> new ItemFrameTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Jukebox", 2, in -> new JukeboxTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Lectern", 2, in -> new LecternTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("MobSpawner", 2, in -> new MobSpawnerTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Music", 2, in -> new NoteblockTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("PistonArm", 2, in -> new PistonArmTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("ShulkerBox", 2, in -> new ShulkerBoxTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Sign", 3, in -> new SignTileEntity( (Block) in[0], (Items) in[1], (EventCaller) in[2] ));
    registry.registerAdditionalConstructor("Skull", 2, in -> new SkullTileEntity( (Block) in[0], (Items) in[1] ));
    registry.registerAdditionalConstructor("Smoker", 2, in -> new SmokerTileEntity( (Block) in[0], (Items) in[1] ));
  }

}