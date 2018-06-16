package io.gomint.server.world.block;

import io.gomint.entity.Entity;
import io.gomint.event.world.BlockPlaceEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.registry.Registry;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.generator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Blocks {

    private static final Logger LOGGER = LoggerFactory.getLogger( Blocks.class );
    private static long lastReport = 0;
    private final Registry<BlockGenerator> generators;

    /**
     * Create a new block registry
     *
     * @param server which builds this registry
     */
    public Blocks( GoMintServer server ) {
        this.generators = new Registry<>( server, clazz -> {
            System.out.println( "this.generators.register( " + clazz.getSimpleName() + ".class, new " + clazz.getSimpleName() + "Generator() );" );

            try {
                // Use the same code source as the Gomint JAR
                return (BlockGenerator) ClassLoader.getSystemClassLoader().loadClass( "io.gomint.server.world.block.generator." + clazz.getSimpleName() + "Generator" ).newInstance();
            } catch ( InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
                LOGGER.error( "Could not load block generator", e );
            }

            return null;
        } );

        // this.generators.register( "io.gomint.server.world.block" ); // There for debug purpose (when new blocks gets added)
        this.generators.register( AcaciaFenceGate.class, new AcaciaFenceGateGenerator() );
        this.generators.register( AcaciaLeaves.class, new AcaciaLeavesGenerator() );
        this.generators.register( AcaciaWood.class, new AcaciaWoodGenerator() );
        this.generators.register( AcaciaWoodStairs.class, new AcaciaWoodStairsGenerator() );
        this.generators.register( ActivatorRail.class, new ActivatorRailGenerator() );
        this.generators.register( Air.class, new AirGenerator() );
        this.generators.register( Anvil.class, new AnvilGenerator() );
        this.generators.register( Beacon.class, new BeaconGenerator() );
        this.generators.register( Bed.class, new BedGenerator() );
        this.generators.register( Bedrock.class, new BedrockGenerator() );
        this.generators.register( Beetroot.class, new BeetrootGenerator() );
        this.generators.register( BirchFenceGate.class, new BirchFenceGateGenerator() );
        this.generators.register( BirchWoodStairs.class, new BirchWoodStairsGenerator() );
        this.generators.register( BlackGlazedTerracotta.class, new BlackGlazedTerracottaGenerator() );
        this.generators.register( BlockMovedByPiston.class, new BlockMovedByPistonGenerator() );
        this.generators.register( BlockOfBones.class, new BlockOfBonesGenerator() );
        this.generators.register( BlockOfCoal.class, new BlockOfCoalGenerator() );
        this.generators.register( BlockOfDiamond.class, new BlockOfDiamondGenerator() );
        this.generators.register( BlockOfEmerald.class, new BlockOfEmeraldGenerator() );
        this.generators.register( BlockOfGold.class, new BlockOfGoldGenerator() );
        this.generators.register( BlockOfIron.class, new BlockOfIronGenerator() );
        this.generators.register( BlockOfQuartz.class, new BlockOfQuartzGenerator() );
        this.generators.register( BlockOfRedstone.class, new BlockOfRedstoneGenerator() );
        this.generators.register( BlueGlazedTerracotta.class, new BlueGlazedTerracottaGenerator() );
        this.generators.register( Bookshelf.class, new BookshelfGenerator() );
        this.generators.register( BrewingStand.class, new BrewingStandGenerator() );
        this.generators.register( BrickBlock.class, new BrickBlockGenerator() );
        this.generators.register( BrickStairs.class, new BrickStairsGenerator() );
        this.generators.register( BrownGlazedTerracotta.class, new BrownGlazedTerracottaGenerator() );
        this.generators.register( BrownMushroom.class, new BrownMushroomGenerator() );
        this.generators.register( BrownMushroomBlock.class, new BrownMushroomBlockGenerator() );
        this.generators.register( BurningFurnace.class, new BurningFurnaceGenerator() );
        this.generators.register( Cactus.class, new CactusGenerator() );
        this.generators.register( Cake.class, new CakeGenerator() );
        this.generators.register( Carpet.class, new CarpetGenerator() );
        this.generators.register( Carrots.class, new CarrotsGenerator() );
        this.generators.register( Cauldron.class, new CauldronGenerator() );
        this.generators.register( ChainCommandBlock.class, new ChainCommandBlockGenerator() );
        this.generators.register( Chest.class, new ChestGenerator() );
        this.generators.register( ChorusFlower.class, new ChorusFlowerGenerator() );
        this.generators.register( ChorusPlant.class, new ChorusPlantGenerator() );
        this.generators.register( Clay.class, new ClayGenerator() );
        this.generators.register( CoalOre.class, new CoalOreGenerator() );
        this.generators.register( Cobblestone.class, new CobblestoneGenerator() );
        this.generators.register( CobblestoneStairs.class, new CobblestoneStairsGenerator() );
        this.generators.register( CobblestoneWall.class, new CobblestoneWallGenerator() );
        this.generators.register( Cobweb.class, new CobwebGenerator() );
        this.generators.register( Cocoa.class, new CocoaGenerator() );
        this.generators.register( CommandBlock.class, new CommandBlockGenerator() );
        this.generators.register( Concrete.class, new ConcreteGenerator() );
        this.generators.register( ConcretePowder.class, new ConcretePowderGenerator() );
        this.generators.register( CraftingTable.class, new CraftingTableGenerator() );
        this.generators.register( Crops.class, new CropsGenerator() );
        this.generators.register( CyanGlazedTerracotta.class, new CyanGlazedTerracottaGenerator() );
        this.generators.register( Dandelion.class, new DandelionGenerator() );
        this.generators.register( DarkOakFenceGate.class, new DarkOakFenceGateGenerator() );
        this.generators.register( DarkOakWoodStairs.class, new DarkOakWoodStairsGenerator() );
        this.generators.register( DaylightDetector.class, new DaylightDetectorGenerator() );
        this.generators.register( DeadBush.class, new DeadBushGenerator() );
        this.generators.register( DetectorRail.class, new DetectorRailGenerator() );
        this.generators.register( DiamondOre.class, new DiamondOreGenerator() );
        this.generators.register( Dirt.class, new DirtGenerator() );
        this.generators.register( Dispenser.class, new DispenserGenerator() );
        this.generators.register( DoubleRedSandstoneSlab.class, new DoubleRedSandstoneSlabGenerator() );
        this.generators.register( DoubleStoneSlab.class, new DoubleStoneSlabGenerator() );
        this.generators.register( DragonEgg.class, new DragonEggGenerator() );
        this.generators.register( Dropper.class, new DropperGenerator() );
        this.generators.register( EmeraldOre.class, new EmeraldOreGenerator() );
        this.generators.register( EnchantmentTable.class, new EnchantmentTableGenerator() );
        this.generators.register( EndBricks.class, new EndBricksGenerator() );
        this.generators.register( EnderChest.class, new EnderChestGenerator() );
        this.generators.register( EndGateway.class, new EndGatewayGenerator() );
        this.generators.register( EndPortal.class, new EndPortalGenerator() );
        this.generators.register( EndPortalFrame.class, new EndPortalFrameGenerator() );
        this.generators.register( EndRod.class, new EndRodGenerator() );
        this.generators.register( EndStone.class, new EndStoneGenerator() );
        this.generators.register( Farmland.class, new FarmlandGenerator() );
        this.generators.register( Fence.class, new FenceGenerator() );
        this.generators.register( FenceGate.class, new FenceGateGenerator() );
        this.generators.register( Fire.class, new FireGenerator() );
        this.generators.register( Flower.class, new FlowerGenerator() );
        this.generators.register( FlowerPot.class, new FlowerPotGenerator() );
        this.generators.register( FlowingLava.class, new FlowingLavaGenerator() );
        this.generators.register( FlowingWater.class, new FlowingWaterGenerator() );
        this.generators.register( FrostedIce.class, new FrostedIceGenerator() );
        this.generators.register( Furnace.class, new FurnaceGenerator() );
        this.generators.register( Glass.class, new GlassGenerator() );
        this.generators.register( GlassPane.class, new GlassPaneGenerator() );
        this.generators.register( GlowingObsidian.class, new GlowingObsidianGenerator() );
        this.generators.register( GlowingRedstoneOre.class, new GlowingRedstoneOreGenerator() );
        this.generators.register( Glowstone.class, new GlowstoneGenerator() );
        this.generators.register( GoldOre.class, new GoldOreGenerator() );
        this.generators.register( GrassBlock.class, new GrassBlockGenerator() );
        this.generators.register( GrassPath.class, new GrassPathGenerator() );
        this.generators.register( Gravel.class, new GravelGenerator() );
        this.generators.register( GrayGlazedTerracotta.class, new GrayGlazedTerracottaGenerator() );
        this.generators.register( GreenGlazedTerracotta.class, new GreenGlazedTerracottaGenerator() );
        this.generators.register( HardenedClay.class, new HardenedClayGenerator() );
        this.generators.register( HayBale.class, new HayBaleGenerator() );
        this.generators.register( HeavyWeightedPressurePlate.class, new HeavyWeightedPressurePlateGenerator() );
        this.generators.register( Hopper.class, new HopperGenerator() );
        this.generators.register( Ice.class, new IceGenerator() );
        this.generators.register( InvertedDaylightSensor.class, new InvertedDaylightSensorGenerator() );
        this.generators.register( InvisibleBedrock.class, new InvisibleBedrockGenerator() );
        this.generators.register( IronBars.class, new IronBarsGenerator() );
        this.generators.register( IronDoor.class, new IronDoorGenerator() );
        this.generators.register( IronOre.class, new IronOreGenerator() );
        this.generators.register( IronTrapdoor.class, new IronTrapdoorGenerator() );
        this.generators.register( ItemFrame.class, new ItemFrameGenerator() );
        this.generators.register( JackOLantern.class, new JackOLanternGenerator() );
        this.generators.register( Jukebox.class, new JukeboxGenerator() );
        this.generators.register( JungleFenceGate.class, new JungleFenceGateGenerator() );
        this.generators.register( JungleWoodStairs.class, new JungleWoodStairsGenerator() );
        this.generators.register( Ladder.class, new LadderGenerator() );
        this.generators.register( LapisLazuliBlock.class, new LapisLazuliBlockGenerator() );
        this.generators.register( LapisLazuliOre.class, new LapisLazuliOreGenerator() );
        this.generators.register( Leaves.class, new LeavesGenerator() );
        this.generators.register( Lever.class, new LeverGenerator() );
        this.generators.register( LightBlueGlazedTerracotta.class, new LightBlueGlazedTerracottaGenerator() );
        this.generators.register( LightWeightedPressurePlate.class, new LightWeightedPressurePlateGenerator() );
        this.generators.register( LilyPad.class, new LilyPadGenerator() );
        this.generators.register( LimeGlazedTerracotta.class, new LimeGlazedTerracottaGenerator() );
        this.generators.register( Log.class, new LogGenerator() );
        this.generators.register( MagentaGlazedTerracotta.class, new MagentaGlazedTerracottaGenerator() );
        this.generators.register( Magma.class, new MagmaGenerator() );
        this.generators.register( Melon.class, new MelonGenerator() );
        this.generators.register( MelonStem.class, new MelonStemGenerator() );
        this.generators.register( MobSpawner.class, new MobSpawnerGenerator() );
        this.generators.register( MonsterEgg.class, new MonsterEggGenerator() );
        this.generators.register( MossyCobblestone.class, new MossyCobblestoneGenerator() );
        this.generators.register( Mycelium.class, new MyceliumGenerator() );
        this.generators.register( NetherBrick.class, new NetherBrickGenerator() );
        this.generators.register( NetherBrickFence.class, new NetherBrickFenceGenerator() );
        this.generators.register( NetherBrickStairs.class, new NetherBrickStairsGenerator() );
        this.generators.register( NetherQuartzOre.class, new NetherQuartzOreGenerator() );
        this.generators.register( Netherrack.class, new NetherrackGenerator() );
        this.generators.register( NetherReactorCore.class, new NetherReactorCoreGenerator() );
        this.generators.register( NetherWart.class, new NetherWartGenerator() );
        this.generators.register( NetherWartBlock.class, new NetherWartBlockGenerator() );
        this.generators.register( NoteBlock.class, new NoteBlockGenerator() );
        this.generators.register( OakWoodStairs.class, new OakWoodStairsGenerator() );
        this.generators.register( Observer.class, new ObserverGenerator() );
        this.generators.register( Obsidian.class, new ObsidianGenerator() );
        this.generators.register( OrangeGlazedTerracotta.class, new OrangeGlazedTerracottaGenerator() );
        this.generators.register( PackedIce.class, new PackedIceGenerator() );
        this.generators.register( PinkGlazedTerracotta.class, new PinkGlazedTerracottaGenerator() );
        this.generators.register( Piston.class, new PistonGenerator() );
        this.generators.register( PistonHead.class, new PistonHeadGenerator() );
        this.generators.register( Podzol.class, new PodzolGenerator() );
        this.generators.register( Portal.class, new PortalGenerator() );
        this.generators.register( Potato.class, new PotatoGenerator() );
        this.generators.register( PoweredRail.class, new PoweredRailGenerator() );
        this.generators.register( Prismarine.class, new PrismarineGenerator() );
        this.generators.register( Pumpkin.class, new PumpkinGenerator() );
        this.generators.register( PumpkinStem.class, new PumpkinStemGenerator() );
        this.generators.register( PurpleGlazedTerracotta.class, new PurpleGlazedTerracottaGenerator() );
        this.generators.register( PurpurBlock.class, new PurpurBlockGenerator() );
        this.generators.register( PurpurStairs.class, new PurpurStairsGenerator() );
        this.generators.register( QuartzStairs.class, new QuartzStairsGenerator() );
        this.generators.register( Rail.class, new RailGenerator() );
        this.generators.register( RedGlazedTerracotta.class, new RedGlazedTerracottaGenerator() );
        this.generators.register( RedMushroom.class, new RedMushroomGenerator() );
        this.generators.register( RedMushroomBlock.class, new RedMushroomBlockGenerator() );
        this.generators.register( RedNetherBrick.class, new RedNetherBrickGenerator() );
        this.generators.register( RedSandstone.class, new RedSandstoneGenerator() );
        this.generators.register( RedSandstoneSlab.class, new RedSandstoneSlabGenerator() );
        this.generators.register( RedSandstoneStairs.class, new RedSandstoneStairsGenerator() );
        this.generators.register( RedstoneComparatorPowered.class, new RedstoneComparatorPoweredGenerator() );
        this.generators.register( RedstoneComparatorUnpowered.class, new RedstoneComparatorUnpoweredGenerator() );
        this.generators.register( RedstoneLampActive.class, new RedstoneLampActiveGenerator() );
        this.generators.register( RedstoneLampInactive.class, new RedstoneLampInactiveGenerator() );
        this.generators.register( RedstoneOre.class, new RedstoneOreGenerator() );
        this.generators.register( RedstoneRepeaterActive.class, new RedstoneRepeaterActiveGenerator() );
        this.generators.register( RedstoneRepeaterInactive.class, new RedstoneRepeaterInactiveGenerator() );
        this.generators.register( RedstoneTorchActive.class, new RedstoneTorchActiveGenerator() );
        this.generators.register( RedstoneTorchInactive.class, new RedstoneTorchInactiveGenerator() );
        this.generators.register( RedstoneWire.class, new RedstoneWireGenerator() );
        this.generators.register( RepeatingCommandBlock.class, new RepeatingCommandBlockGenerator() );
        this.generators.register( Reserved6.class, new Reserved6Generator() );
        this.generators.register( Sand.class, new SandGenerator() );
        this.generators.register( Sandstone.class, new SandstoneGenerator() );
        this.generators.register( SandstoneStairs.class, new SandstoneStairsGenerator() );
        this.generators.register( Sapling.class, new SaplingGenerator() );
        this.generators.register( SeaLantern.class, new SeaLanternGenerator() );
        this.generators.register( ShulkerBox.class, new ShulkerBoxGenerator() );
        this.generators.register( Sign.class, new SignGenerator() );
        this.generators.register( SilverGlazedTerracotta.class, new SilverGlazedTerracottaGenerator() );
        this.generators.register( Skull.class, new SkullGenerator() );
        this.generators.register( SlimeBlock.class, new SlimeBlockGenerator() );
        this.generators.register( Snow.class, new SnowGenerator() );
        this.generators.register( SnowLayer.class, new SnowLayerGenerator() );
        this.generators.register( SoulSand.class, new SoulSandGenerator() );
        this.generators.register( Sponge.class, new SpongeGenerator() );
        this.generators.register( SpruceFenceGate.class, new SpruceFenceGateGenerator() );
        this.generators.register( SpruceWoodStairs.class, new SpruceWoodStairsGenerator() );
        this.generators.register( StainedGlass.class, new StainedGlassGenerator() );
        this.generators.register( StainedGlassPane.class, new StainedGlassPaneGenerator() );
        this.generators.register( StainedHardenedClay.class, new StainedHardenedClayGenerator() );
        this.generators.register( StandingBanner.class, new StandingBannerGenerator() );
        this.generators.register( StationaryLava.class, new StationaryLavaGenerator() );
        this.generators.register( StationaryWater.class, new StationaryWaterGenerator() );
        this.generators.register( StickyPiston.class, new StickyPistonGenerator() );
        this.generators.register( Stone.class, new StoneGenerator() );
        this.generators.register( StoneBrick.class, new StoneBrickGenerator() );
        this.generators.register( StoneBrickStairs.class, new StoneBrickStairsGenerator() );
        this.generators.register( StoneButton.class, new StoneButtonGenerator() );
        this.generators.register( Stonecutter.class, new StonecutterGenerator() );
        this.generators.register( StonePressurePlate.class, new StonePressurePlateGenerator() );
        this.generators.register( StoneSlab.class, new StoneSlabGenerator() );
        this.generators.register( StructureBlock.class, new StructureBlockGenerator() );
        this.generators.register( SugarCane.class, new SugarCaneGenerator() );
        this.generators.register( Sunflower.class, new SunflowerGenerator() );
        this.generators.register( TallGrass.class, new TallGrassGenerator() );
        this.generators.register( TNT.class, new TNTGenerator() );
        this.generators.register( Torch.class, new TorchGenerator() );
        this.generators.register( Trapdoor.class, new TrapdoorGenerator() );
        this.generators.register( TrappedChest.class, new TrappedChestGenerator() );
        this.generators.register( Tripwire.class, new TripwireGenerator() );
        this.generators.register( TripwireHook.class, new TripwireHookGenerator() );
        this.generators.register( UndyedShulkerBox.class, new UndyedShulkerBoxGenerator() );
        this.generators.register( UpdateGameBlockUpdate1.class, new UpdateGameBlockUpdate1Generator() );
        this.generators.register( UpdateGameBlockUpdate2.class, new UpdateGameBlockUpdate2Generator() );
        this.generators.register( Vines.class, new VinesGenerator() );
        this.generators.register( WallBanner.class, new WallBannerGenerator() );
        this.generators.register( WallSign.class, new WallSignGenerator() );
        this.generators.register( WhiteGlazedTerracotta.class, new WhiteGlazedTerracottaGenerator() );
        this.generators.register( Wood.class, new WoodGenerator() );
        this.generators.register( WoodenButton.class, new WoodenButtonGenerator() );
        this.generators.register( WoodenDoor.class, new WoodenDoorGenerator() );
        this.generators.register( WoodenDoubleSlab.class, new WoodenDoubleSlabGenerator() );
        this.generators.register( WoodenPressurePlate.class, new WoodenPressurePlateGenerator() );
        this.generators.register( WoodenSlab.class, new WoodenSlabGenerator() );
        this.generators.register( Wool.class, new WoolGenerator() );
        this.generators.register( YellowGlazedTerracotta.class, new YellowGlazedTerracottaGenerator() );
    }

    public <T extends Block> T get( int blockId, byte blockData, byte skyLightLevel, byte blockLightLevel,
                                    TileEntity tileEntity, Location location, int layer ) {
        BlockGenerator instance = this.generators.getGenerator( blockId );
        if ( instance != null ) {
            if ( location == null ) {
                return instance.generate();
            }

            return instance.generate( blockId, blockData, skyLightLevel, blockLightLevel, tileEntity, location, layer );
        }

        // Don't spam the report server pls
        if ( System.currentTimeMillis() - lastReport > TimeUnit.SECONDS.toSeconds( 10 ) ) {
            ReportUploader.create().includeWorlds().property( "missing_block", String.valueOf( blockId ) ).upload();
            lastReport = System.currentTimeMillis();
        }

        LOGGER.warn( "Unknown block {} @ {}", blockId, location );
        return null;
    }

    public Block get( int blockId ) {
        BlockGenerator instance = this.generators.getGenerator( blockId );
        if ( instance != null ) {
            return instance.generate();
        }

        LOGGER.warn( "Unknown block {}", blockId );
        return null;
    }

    public <T extends Block> T get( Class<?> apiInterface ) {
        BlockGenerator instance = this.generators.getGenerator( apiInterface );
        if ( instance != null ) {
            return instance.generate();
        }

        return null;
    }

    public int getID( Class<?> block ) {
        return this.generators.getId( block );
    }

    public boolean replaceWithItem( EntityPlayer entity, Block clickedBlock, Block block, ItemStack item, Vector clickVector ) {
        // We need to change the block id first
        int id = ( (io.gomint.server.inventory.item.ItemStack) item ).getBlockId();
        BlockGenerator blockGenerator = this.generators.getGenerator( id );
        Block newBlock = blockGenerator.generate();
        if ( !newBlock.beforePlacement( entity, item, block.location ) ) {
            return false;
        }

        WorldAdapter adapter = (WorldAdapter) block.location.getWorld();
        PlacementData data = newBlock.calculatePlacementData( entity, item, clickVector );

        // Check only solid blocks for bounding box intersects
        if ( newBlock.isSolid() ) {
            newBlock.setLocation( block.location ); // Temp setting, needed for getting bounding boxes
            newBlock.setBlockData( data.getMetaData() );
            newBlock.generateBlockStates();

            for ( AxisAlignedBB bb : newBlock.getBoundingBox() ) {
                // Check other entities in the bounding box
                Collection<Entity> collidedWith = adapter.getNearbyEntities( bb, null, null );
                if ( collidedWith != null ) {
                    return false;
                }
            }
        }

        // We decided that the block would fit
        BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent( entity, clickedBlock, block, item, newBlock );
        block.world.getServer().getPluginManager().callEvent( blockPlaceEvent );

        if ( blockPlaceEvent.isCancelled() ) {
            return false;
        }


        block = block.setBlockFromPlacementData( data );
        block.afterPlacement( data );
        return true;
    }

}
