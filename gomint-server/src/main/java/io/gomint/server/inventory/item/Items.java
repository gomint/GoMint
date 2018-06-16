package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.GoMintServer;
import io.gomint.server.inventory.item.generator.ItemGenerator;
import io.gomint.server.registry.Registry;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.server.inventory.item.generator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Items {

    private static final Logger LOGGER = LoggerFactory.getLogger( Items.class );
    private final Registry<ItemGenerator> generators;

    /**
     * Create a new item registry
     *
     * @param server which builds this registry
     */
    public Items( GoMintServer server ) {
        this.generators = new Registry<>( server, clazz -> {
            System.out.println( "this.generators.register( " + clazz.getSimpleName() + ".class, new " + clazz.getSimpleName() + "Generator() );");

            try {
                return (ItemGenerator) ClassLoader.getSystemClassLoader().loadClass( "io.gomint.server.inventory.item.generator." + clazz.getSimpleName() + "Generator" ).newInstance();
            } catch ( ClassNotFoundException | IllegalAccessException | InstantiationException e1 ) {
                LOGGER.error( "Could not use pre generated generator: ", e1 );
            }

            return null;
        } );

        // this.generators.register( "io.gomint.server.inventory.item" );

        this.generators.register( ItemAcaciaDoorBlock.class, new ItemAcaciaDoorBlockGenerator() );
        this.generators.register( ItemAcaciaLeaves.class, new ItemAcaciaLeavesGenerator() );
        this.generators.register( ItemAcaciaWood.class, new ItemAcaciaWoodGenerator() );
        this.generators.register( ItemAcaciaWoodDoor.class, new ItemAcaciaWoodDoorGenerator() );
        this.generators.register( ItemAcaciaWoodFenceGate.class, new ItemAcaciaWoodFenceGateGenerator() );
        this.generators.register( ItemAcaciaWoodStairs.class, new ItemAcaciaWoodStairsGenerator() );
        this.generators.register( ItemActivatorRail.class, new ItemActivatorRailGenerator() );
        this.generators.register( ItemAir.class, new ItemAirGenerator() );
        this.generators.register( ItemAnvil.class, new ItemAnvilGenerator() );
        this.generators.register( ItemApple.class, new ItemAppleGenerator() );
        this.generators.register( ItemArmorStand.class, new ItemArmorStandGenerator() );
        this.generators.register( ItemArrow.class, new ItemArrowGenerator() );
        this.generators.register( ItemBakedPotato.class, new ItemBakedPotatoGenerator() );
        this.generators.register( ItemBanner.class, new ItemBannerGenerator() );
        this.generators.register( ItemBeacon.class, new ItemBeaconGenerator() );
        this.generators.register( ItemBed.class, new ItemBedGenerator() );
        this.generators.register( ItemBedBlock.class, new ItemBedBlockGenerator() );
        this.generators.register( ItemBedrock.class, new ItemBedrockGenerator() );
        this.generators.register( ItemBeetroot.class, new ItemBeetrootGenerator() );
        this.generators.register( ItemBeetrootBlock.class, new ItemBeetrootBlockGenerator() );
        this.generators.register( ItemBeetrootSeeds.class, new ItemBeetrootSeedsGenerator() );
        this.generators.register( ItemBeetrootSoup.class, new ItemBeetrootSoupGenerator() );
        this.generators.register( ItemBirchDoorBlock.class, new ItemBirchDoorBlockGenerator() );
        this.generators.register( ItemBirchWoodDoor.class, new ItemBirchWoodDoorGenerator() );
        this.generators.register( ItemBirchWoodFenceGate.class, new ItemBirchWoodFenceGateGenerator() );
        this.generators.register( ItemBirchWoodStairs.class, new ItemBirchWoodStairsGenerator() );
        this.generators.register( ItemBlackGlazedTerracotta.class, new ItemBlackGlazedTerracottaGenerator() );
        this.generators.register( ItemBlazePowder.class, new ItemBlazePowderGenerator() );
        this.generators.register( ItemBlazeRod.class, new ItemBlazeRodGenerator() );
        this.generators.register( ItemBlockMovedByPiston.class, new ItemBlockMovedByPistonGenerator() );
        this.generators.register( ItemBlockOfBones.class, new ItemBlockOfBonesGenerator() );
        this.generators.register( ItemBlockOfCoal.class, new ItemBlockOfCoalGenerator() );
        this.generators.register( ItemBlockOfDiamond.class, new ItemBlockOfDiamondGenerator() );
        this.generators.register( ItemBlockOfEmerald.class, new ItemBlockOfEmeraldGenerator() );
        this.generators.register( ItemBlockOfGold.class, new ItemBlockOfGoldGenerator() );
        this.generators.register( ItemBlockOfIron.class, new ItemBlockOfIronGenerator() );
        this.generators.register( ItemBlockOfLapisLazuli.class, new ItemBlockOfLapisLazuliGenerator() );
        this.generators.register( ItemBlockOfQuartz.class, new ItemBlockOfQuartzGenerator() );
        this.generators.register( ItemBlockOfRedstone.class, new ItemBlockOfRedstoneGenerator() );
        this.generators.register( ItemBlueGlazedTerracotta.class, new ItemBlueGlazedTerracottaGenerator() );
        this.generators.register( ItemBoat.class, new ItemBoatGenerator() );
        this.generators.register( ItemBone.class, new ItemBoneGenerator() );
        this.generators.register( ItemBook.class, new ItemBookGenerator() );
        this.generators.register( ItemBookshelf.class, new ItemBookshelfGenerator() );
        this.generators.register( ItemBow.class, new ItemBowGenerator() );
        this.generators.register( ItemBowl.class, new ItemBowlGenerator() );
        this.generators.register( ItemBread.class, new ItemBreadGenerator() );
        this.generators.register( ItemBrewingStand.class, new ItemBrewingStandGenerator() );
        this.generators.register( ItemBrewingStandBlock.class, new ItemBrewingStandBlockGenerator() );
        this.generators.register( ItemBrick.class, new ItemBrickGenerator() );
        this.generators.register( ItemBrickBlock.class, new ItemBrickBlockGenerator() );
        this.generators.register( ItemBrickStairs.class, new ItemBrickStairsGenerator() );
        this.generators.register( ItemBrownGlazedTerracotta.class, new ItemBrownGlazedTerracottaGenerator() );
        this.generators.register( ItemBrownMushroom.class, new ItemBrownMushroomGenerator() );
        this.generators.register( ItemBrownMushroomBlock.class, new ItemBrownMushroomBlockGenerator() );
        this.generators.register( ItemBucket.class, new ItemBucketGenerator() );
        this.generators.register( ItemBurningFurnace.class, new ItemBurningFurnaceGenerator() );
        this.generators.register( ItemCactus.class, new ItemCactusGenerator() );
        this.generators.register( ItemCake.class, new ItemCakeGenerator() );
        this.generators.register( ItemCakeBlock.class, new ItemCakeBlockGenerator() );
        this.generators.register( ItemCarpet.class, new ItemCarpetGenerator() );
        this.generators.register( ItemCarrot.class, new ItemCarrotGenerator() );
        this.generators.register( ItemCarrotBlock.class, new ItemCarrotBlockGenerator() );
        this.generators.register( ItemCarrotOnAStick.class, new ItemCarrotOnAStickGenerator() );
        this.generators.register( ItemCauldron.class, new ItemCauldronGenerator() );
        this.generators.register( ItemCauldronBlock.class, new ItemCauldronBlockGenerator() );
        this.generators.register( ItemChainBoots.class, new ItemChainBootsGenerator() );
        this.generators.register( ItemChainChestplate.class, new ItemChainChestplateGenerator() );
        this.generators.register( ItemChainCommandBlock.class, new ItemChainCommandBlockGenerator() );
        this.generators.register( ItemChainHelmet.class, new ItemChainHelmetGenerator() );
        this.generators.register( ItemChainLeggings.class, new ItemChainLeggingsGenerator() );
        this.generators.register( ItemChest.class, new ItemChestGenerator() );
        this.generators.register( ItemChorusFlower.class, new ItemChorusFlowerGenerator() );
        this.generators.register( ItemChorusFruit.class, new ItemChorusFruitGenerator() );
        this.generators.register( ItemChorusPlant.class, new ItemChorusPlantGenerator() );
        this.generators.register( ItemClay.class, new ItemClayGenerator() );
        this.generators.register( ItemClayBall.class, new ItemClayBallGenerator() );
        this.generators.register( ItemClock.class, new ItemClockGenerator() );
        this.generators.register( ItemClownfish.class, new ItemClownfishGenerator() );
        this.generators.register( ItemCoal.class, new ItemCoalGenerator() );
        this.generators.register( ItemCoalOre.class, new ItemCoalOreGenerator() );
        this.generators.register( ItemCobblestone.class, new ItemCobblestoneGenerator() );
        this.generators.register( ItemCobblestoneStairs.class, new ItemCobblestoneStairsGenerator() );
        this.generators.register( ItemCobblestoneWall.class, new ItemCobblestoneWallGenerator() );
        this.generators.register( ItemCobweb.class, new ItemCobwebGenerator() );
        this.generators.register( ItemCocoa.class, new ItemCocoaGenerator() );
        this.generators.register( ItemCommandBlock.class, new ItemCommandBlockGenerator() );
        this.generators.register( ItemCompass.class, new ItemCompassGenerator() );
        this.generators.register( ItemConcrete.class, new ItemConcreteGenerator() );
        this.generators.register( ItemConcretePowder.class, new ItemConcretePowderGenerator() );
        this.generators.register( ItemCookedBeef.class, new ItemCookedBeefGenerator() );
        this.generators.register( ItemCookedChicken.class, new ItemCookedChickenGenerator() );
        this.generators.register( ItemCookedFish.class, new ItemCookedFishGenerator() );
        this.generators.register( ItemCookedMutton.class, new ItemCookedMuttonGenerator() );
        this.generators.register( ItemCookedPorkchop.class, new ItemCookedPorkchopGenerator() );
        this.generators.register( ItemCookedRabbit.class, new ItemCookedRabbitGenerator() );
        this.generators.register( ItemCookedSalmon.class, new ItemCookedSalmonGenerator() );
        this.generators.register( ItemCookie.class, new ItemCookieGenerator() );
        this.generators.register( ItemCraftingTable.class, new ItemCraftingTableGenerator() );
        this.generators.register( ItemCrops.class, new ItemCropsGenerator() );
        this.generators.register( ItemCyanGlazedTerracotta.class, new ItemCyanGlazedTerracottaGenerator() );
        this.generators.register( ItemDandelion.class, new ItemDandelionGenerator() );
        this.generators.register( ItemDarkOakDoorBlock.class, new ItemDarkOakDoorBlockGenerator() );
        this.generators.register( ItemDarkOakWoodDoor.class, new ItemDarkOakWoodDoorGenerator() );
        this.generators.register( ItemDarkOakWoodFenceGate.class, new ItemDarkOakWoodFenceGateGenerator() );
        this.generators.register( ItemDarkOakWoodStairs.class, new ItemDarkOakWoodStairsGenerator() );
        this.generators.register( ItemDaylightDetector.class, new ItemDaylightDetectorGenerator() );
        this.generators.register( ItemDeadBush.class, new ItemDeadBushGenerator() );
        this.generators.register( ItemDetectorRail.class, new ItemDetectorRailGenerator() );
        this.generators.register( ItemDiamond.class, new ItemDiamondGenerator() );
        this.generators.register( ItemDiamondAxe.class, new ItemDiamondAxeGenerator() );
        this.generators.register( ItemDiamondBoots.class, new ItemDiamondBootsGenerator() );
        this.generators.register( ItemDiamondChestplate.class, new ItemDiamondChestplateGenerator() );
        this.generators.register( ItemDiamondHelmet.class, new ItemDiamondHelmetGenerator() );
        this.generators.register( ItemDiamondHoe.class, new ItemDiamondHoeGenerator() );
        this.generators.register( ItemDiamondHorseArmor.class, new ItemDiamondHorseArmorGenerator() );
        this.generators.register( ItemDiamondLeggings.class, new ItemDiamondLeggingsGenerator() );
        this.generators.register( ItemDiamondOre.class, new ItemDiamondOreGenerator() );
        this.generators.register( ItemDiamondPickaxe.class, new ItemDiamondPickaxeGenerator() );
        this.generators.register( ItemDiamondShovel.class, new ItemDiamondShovelGenerator() );
        this.generators.register( ItemDiamondSword.class, new ItemDiamondSwordGenerator() );
        this.generators.register( ItemDirt.class, new ItemDirtGenerator() );
        this.generators.register( ItemDispenser.class, new ItemDispenserGenerator() );
        this.generators.register( ItemDoubleRedSandstoneSlab.class, new ItemDoubleRedSandstoneSlabGenerator() );
        this.generators.register( ItemDoubleStoneSlab.class, new ItemDoubleStoneSlabGenerator() );
        this.generators.register( ItemDragonBreath.class, new ItemDragonBreathGenerator() );
        this.generators.register( ItemDragonEgg.class, new ItemDragonEggGenerator() );
        this.generators.register( ItemDropper.class, new ItemDropperGenerator() );
        this.generators.register( ItemDye.class, new ItemDyeGenerator() );
        this.generators.register( ItemEgg.class, new ItemEggGenerator() );
        this.generators.register( ItemElytra.class, new ItemElytraGenerator() );
        this.generators.register( ItemEmerald.class, new ItemEmeraldGenerator() );
        this.generators.register( ItemEmeraldOre.class, new ItemEmeraldOreGenerator() );
        this.generators.register( ItemEnchantedBook.class, new ItemEnchantedBookGenerator() );
        this.generators.register( ItemEnchantedGoldenApple.class, new ItemEnchantedGoldenAppleGenerator() );
        this.generators.register( ItemEnchantmentTable.class, new ItemEnchantmentTableGenerator() );
        this.generators.register( ItemEndBricks.class, new ItemEndBricksGenerator() );
        this.generators.register( ItemEndCrystal.class, new ItemEndCrystalGenerator() );
        this.generators.register( ItemEnderChest.class, new ItemEnderChestGenerator() );
        this.generators.register( ItemEnderPearl.class, new ItemEnderPearlGenerator() );
        this.generators.register( ItemEndGateway.class, new ItemEndGatewayGenerator() );
        this.generators.register( ItemEndPortal.class, new ItemEndPortalGenerator() );
        this.generators.register( ItemEndPortalFrame.class, new ItemEndPortalFrameGenerator() );
        this.generators.register( ItemEndRod.class, new ItemEndRodGenerator() );
        this.generators.register( ItemEndStone.class, new ItemEndStoneGenerator() );
        this.generators.register( ItemExperienceBottle.class, new ItemExperienceBottleGenerator() );
        this.generators.register( ItemEyeOfEnder.class, new ItemEyeOfEnderGenerator() );
        this.generators.register( ItemFarmland.class, new ItemFarmlandGenerator() );
        this.generators.register( ItemFeather.class, new ItemFeatherGenerator() );
        this.generators.register( ItemFence.class, new ItemFenceGenerator() );
        this.generators.register( ItemFenceGate.class, new ItemFenceGateGenerator() );
        this.generators.register( ItemFermentedSpiderEye.class, new ItemFermentedSpiderEyeGenerator() );
        this.generators.register( ItemFilledMap.class, new ItemFilledMapGenerator() );
        this.generators.register( ItemFire.class, new ItemFireGenerator() );
        this.generators.register( ItemFireCharge.class, new ItemFireChargeGenerator() );
        this.generators.register( ItemFirework.class, new ItemFireworkGenerator() );
        this.generators.register( ItemFireworkCharge.class, new ItemFireworkChargeGenerator() );
        this.generators.register( ItemFishingRod.class, new ItemFishingRodGenerator() );
        this.generators.register( ItemFlint.class, new ItemFlintGenerator() );
        this.generators.register( ItemFlintAndSteel.class, new ItemFlintAndSteelGenerator() );
        this.generators.register( ItemFlower.class, new ItemFlowerGenerator() );
        this.generators.register( ItemFlowerPot.class, new ItemFlowerPotGenerator() );
        this.generators.register( ItemFlowerPotBlock.class, new ItemFlowerPotBlockGenerator() );
        this.generators.register( ItemFlowingLava.class, new ItemFlowingLavaGenerator() );
        this.generators.register( ItemFlowingWater.class, new ItemFlowingWaterGenerator() );
        this.generators.register( ItemFrostedIce.class, new ItemFrostedIceGenerator() );
        this.generators.register( ItemFurnace.class, new ItemFurnaceGenerator() );
        this.generators.register( ItemGhastTear.class, new ItemGhastTearGenerator() );
        this.generators.register( ItemGlass.class, new ItemGlassGenerator() );
        this.generators.register( ItemGlassBottle.class, new ItemGlassBottleGenerator() );
        this.generators.register( ItemGlassPane.class, new ItemGlassPaneGenerator() );
        this.generators.register( ItemGlisteringMelon.class, new ItemGlisteringMelonGenerator() );
        this.generators.register( ItemGlowingObsidian.class, new ItemGlowingObsidianGenerator() );
        this.generators.register( ItemGlowingRedstoneOre.class, new ItemGlowingRedstoneOreGenerator() );
        this.generators.register( ItemGlowstone.class, new ItemGlowstoneGenerator() );
        this.generators.register( ItemGlowstoneDust.class, new ItemGlowstoneDustGenerator() );
        this.generators.register( ItemGoldenApple.class, new ItemGoldenAppleGenerator() );
        this.generators.register( ItemGoldenAxe.class, new ItemGoldenAxeGenerator() );
        this.generators.register( ItemGoldenBoots.class, new ItemGoldenBootsGenerator() );
        this.generators.register( ItemGoldenCarrot.class, new ItemGoldenCarrotGenerator() );
        this.generators.register( ItemGoldenChestplate.class, new ItemGoldenChestplateGenerator() );
        this.generators.register( ItemGoldenHelmet.class, new ItemGoldenHelmetGenerator() );
        this.generators.register( ItemGoldenHoe.class, new ItemGoldenHoeGenerator() );
        this.generators.register( ItemGoldenHorseArmor.class, new ItemGoldenHorseArmorGenerator() );
        this.generators.register( ItemGoldenLeggings.class, new ItemGoldenLeggingsGenerator() );
        this.generators.register( ItemGoldenPickaxe.class, new ItemGoldenPickaxeGenerator() );
        this.generators.register( ItemGoldenShovel.class, new ItemGoldenShovelGenerator() );
        this.generators.register( ItemGoldenSword.class, new ItemGoldenSwordGenerator() );
        this.generators.register( ItemGoldIngot.class, new ItemGoldIngotGenerator() );
        this.generators.register( ItemGoldNugget.class, new ItemGoldNuggetGenerator() );
        this.generators.register( ItemGoldOre.class, new ItemGoldOreGenerator() );
        this.generators.register( ItemGrassBlock.class, new ItemGrassBlockGenerator() );
        this.generators.register( ItemGrassPath.class, new ItemGrassPathGenerator() );
        this.generators.register( ItemGravel.class, new ItemGravelGenerator() );
        this.generators.register( ItemGrayGlazedTerracotta.class, new ItemGrayGlazedTerracottaGenerator() );
        this.generators.register( ItemGreenGlazedTerracotta.class, new ItemGreenGlazedTerracottaGenerator() );
        this.generators.register( ItemGunpowder.class, new ItemGunpowderGenerator() );
        this.generators.register( ItemHardenedClay.class, new ItemHardenedClayGenerator() );
        this.generators.register( ItemHayBale.class, new ItemHayBaleGenerator() );
        this.generators.register( ItemHeavyWeightedPressurePlate.class, new ItemHeavyWeightedPressurePlateGenerator() );
        this.generators.register( ItemHopper.class, new ItemHopperGenerator() );
        this.generators.register( ItemHopperBlock.class, new ItemHopperBlockGenerator() );
        this.generators.register( ItemIce.class, new ItemIceGenerator() );
        this.generators.register( ItemInvertedDaylightSensor.class, new ItemInvertedDaylightSensorGenerator() );
        this.generators.register( ItemInvisibleBedrock.class, new ItemInvisibleBedrockGenerator() );
        this.generators.register( ItemIronAxe.class, new ItemIronAxeGenerator() );
        this.generators.register( ItemIronBars.class, new ItemIronBarsGenerator() );
        this.generators.register( ItemIronBoots.class, new ItemIronBootsGenerator() );
        this.generators.register( ItemIronChestplate.class, new ItemIronChestplateGenerator() );
        this.generators.register( ItemIronDoor.class, new ItemIronDoorGenerator() );
        this.generators.register( ItemIronDoorBlock.class, new ItemIronDoorBlockGenerator() );
        this.generators.register( ItemIronHelmet.class, new ItemIronHelmetGenerator() );
        this.generators.register( ItemIronHoe.class, new ItemIronHoeGenerator() );
        this.generators.register( ItemIronHorseArmor.class, new ItemIronHorseArmorGenerator() );
        this.generators.register( ItemIronIngot.class, new ItemIronIngotGenerator() );
        this.generators.register( ItemIronLeggings.class, new ItemIronLeggingsGenerator() );
        this.generators.register( ItemIronNugget.class, new ItemIronNuggetGenerator() );
        this.generators.register( ItemIronOre.class, new ItemIronOreGenerator() );
        this.generators.register( ItemIronPickaxe.class, new ItemIronPickaxeGenerator() );
        this.generators.register( ItemIronShovel.class, new ItemIronShovelGenerator() );
        this.generators.register( ItemIronSword.class, new ItemIronSwordGenerator() );
        this.generators.register( ItemIronTrapdoor.class, new ItemIronTrapdoorGenerator() );
        this.generators.register( ItemItemFrame.class, new ItemItemFrameGenerator() );
        this.generators.register( ItemItemFrameBlock.class, new ItemItemFrameBlockGenerator() );
        this.generators.register( ItemJackOLantern.class, new ItemJackOLanternGenerator() );
        this.generators.register( ItemJukebox.class, new ItemJukeboxGenerator() );
        this.generators.register( ItemJungleDoorBlock.class, new ItemJungleDoorBlockGenerator() );
        this.generators.register( ItemJungleWoodDoor.class, new ItemJungleWoodDoorGenerator() );
        this.generators.register( ItemJungleWoodFenceGate.class, new ItemJungleWoodFenceGateGenerator() );
        this.generators.register( ItemJungleWoodStairs.class, new ItemJungleWoodStairsGenerator() );
        this.generators.register( ItemLadder.class, new ItemLadderGenerator() );
        this.generators.register( ItemLapisLazuliOre.class, new ItemLapisLazuliOreGenerator() );
        this.generators.register( ItemLead.class, new ItemLeadGenerator() );
        this.generators.register( ItemLeather.class, new ItemLeatherGenerator() );
        this.generators.register( ItemLeatherBoots.class, new ItemLeatherBootsGenerator() );
        this.generators.register( ItemLeatherChestplate.class, new ItemLeatherChestplateGenerator() );
        this.generators.register( ItemLeatherHelmet.class, new ItemLeatherHelmetGenerator() );
        this.generators.register( ItemLeatherHorseArmor.class, new ItemLeatherHorseArmorGenerator() );
        this.generators.register( ItemLeatherLeggings.class, new ItemLeatherLeggingsGenerator() );
        this.generators.register( ItemLeaves.class, new ItemLeavesGenerator() );
        this.generators.register( ItemLever.class, new ItemLeverGenerator() );
        this.generators.register( ItemLightBlueGlazedTerracotta.class, new ItemLightBlueGlazedTerracottaGenerator() );
        this.generators.register( ItemLightWeightedPressurePlate.class, new ItemLightWeightedPressurePlateGenerator() );
        this.generators.register( ItemLilyPad.class, new ItemLilyPadGenerator() );
        this.generators.register( ItemLimeGlazedTerracotta.class, new ItemLimeGlazedTerracottaGenerator() );
        this.generators.register( ItemLingeringPotion.class, new ItemLingeringPotionGenerator() );
        this.generators.register( ItemLog.class, new ItemLogGenerator() );
        this.generators.register( ItemMagentaGlazedTerracotta.class, new ItemMagentaGlazedTerracottaGenerator() );
        this.generators.register( ItemMagma.class, new ItemMagmaGenerator() );
        this.generators.register( ItemMagmaCream.class, new ItemMagmaCreamGenerator() );
        this.generators.register( ItemMap.class, new ItemMapGenerator() );
        this.generators.register( ItemMelon.class, new ItemMelonGenerator() );
        this.generators.register( ItemMelonBlock.class, new ItemMelonBlockGenerator() );
        this.generators.register( ItemMelonSeeds.class, new ItemMelonSeedsGenerator() );
        this.generators.register( ItemMelonStem.class, new ItemMelonStemGenerator() );
        this.generators.register( ItemMinecart.class, new ItemMinecartGenerator() );
        this.generators.register( ItemMinecartWithChest.class, new ItemMinecartWithChestGenerator() );
        this.generators.register( ItemMinecartWithCommandBlock.class, new ItemMinecartWithCommandBlockGenerator() );
        this.generators.register( ItemMinecartWithHopper.class, new ItemMinecartWithHopperGenerator() );
        this.generators.register( ItemMinecartWithTnt.class, new ItemMinecartWithTntGenerator() );
        this.generators.register( ItemMobSpawner.class, new ItemMobSpawnerGenerator() );
        this.generators.register( ItemMonsterEgg.class, new ItemMonsterEggGenerator() );
        this.generators.register( ItemMossyCobblestone.class, new ItemMossyCobblestoneGenerator() );
        this.generators.register( ItemMushroomStew.class, new ItemMushroomStewGenerator() );
        this.generators.register( ItemMycelium.class, new ItemMyceliumGenerator() );
        this.generators.register( ItemNameTag.class, new ItemNameTagGenerator() );
        this.generators.register( ItemNetherBrick.class, new ItemNetherBrickGenerator() );
        this.generators.register( ItemNetherBrickBlock.class, new ItemNetherBrickBlockGenerator() );
        this.generators.register( ItemNetherBrickFence.class, new ItemNetherBrickFenceGenerator() );
        this.generators.register( ItemNetherBrickStairs.class, new ItemNetherBrickStairsGenerator() );
        this.generators.register( ItemNetherQuartz.class, new ItemNetherQuartzGenerator() );
        this.generators.register( ItemNetherQuartzOre.class, new ItemNetherQuartzOreGenerator() );
        this.generators.register( ItemNetherrack.class, new ItemNetherrackGenerator() );
        this.generators.register( ItemNetherReactorCore.class, new ItemNetherReactorCoreGenerator() );
        this.generators.register( ItemNetherStar.class, new ItemNetherStarGenerator() );
        this.generators.register( ItemNetherWart.class, new ItemNetherWartGenerator() );
        this.generators.register( ItemNetherWartBlock.class, new ItemNetherWartBlockGenerator() );
        this.generators.register( ItemNoteBlock.class, new ItemNoteBlockGenerator() );
        this.generators.register( ItemOakWoodDoor.class, new ItemOakWoodDoorGenerator() );
        this.generators.register( ItemOakWoodStairs.class, new ItemOakWoodStairsGenerator() );
        this.generators.register( ItemObserver.class, new ItemObserverGenerator() );
        this.generators.register( ItemObsidian.class, new ItemObsidianGenerator() );
        this.generators.register( ItemOrangeGlazedTerracotta.class, new ItemOrangeGlazedTerracottaGenerator() );
        this.generators.register( ItemPackedIce.class, new ItemPackedIceGenerator() );
        this.generators.register( ItemPainting.class, new ItemPaintingGenerator() );
        this.generators.register( ItemPaper.class, new ItemPaperGenerator() );
        this.generators.register( ItemPinkGlazedTerracotta.class, new ItemPinkGlazedTerracottaGenerator() );
        this.generators.register( ItemPiston.class, new ItemPistonGenerator() );
        this.generators.register( ItemPistonHead.class, new ItemPistonHeadGenerator() );
        this.generators.register( ItemPodzol.class, new ItemPodzolGenerator() );
        this.generators.register( ItemPoisonousPotato.class, new ItemPoisonousPotatoGenerator() );
        this.generators.register( ItemPoppedChorusFruit.class, new ItemPoppedChorusFruitGenerator() );
        this.generators.register( ItemPortal.class, new ItemPortalGenerator() );
        this.generators.register( ItemPotato.class, new ItemPotatoGenerator() );
        this.generators.register( ItemPotatoBlock.class, new ItemPotatoBlockGenerator() );
        this.generators.register( ItemPotion.class, new ItemPotionGenerator() );
        this.generators.register( ItemPoweredRail.class, new ItemPoweredRailGenerator() );
        this.generators.register( ItemPrismarine.class, new ItemPrismarineGenerator() );
        this.generators.register( ItemPrismarineCrystals.class, new ItemPrismarineCrystalsGenerator() );
        this.generators.register( ItemPrismarineShard.class, new ItemPrismarineShardGenerator() );
        this.generators.register( ItemPufferfish.class, new ItemPufferfishGenerator() );
        this.generators.register( ItemPumpkin.class, new ItemPumpkinGenerator() );
        this.generators.register( ItemPumpkinPie.class, new ItemPumpkinPieGenerator() );
        this.generators.register( ItemPumpkinSeeds.class, new ItemPumpkinSeedsGenerator() );
        this.generators.register( ItemPumpkinStem.class, new ItemPumpkinStemGenerator() );
        this.generators.register( ItemPurpleGlazedTerracotta.class, new ItemPurpleGlazedTerracottaGenerator() );
        this.generators.register( ItemPurpurBlock.class, new ItemPurpurBlockGenerator() );
        this.generators.register( ItemPurpurStairs.class, new ItemPurpurStairsGenerator() );
        this.generators.register( ItemQuartzStairs.class, new ItemQuartzStairsGenerator() );
        this.generators.register( ItemRabbitFoot.class, new ItemRabbitFootGenerator() );
        this.generators.register( ItemRabbitHide.class, new ItemRabbitHideGenerator() );
        this.generators.register( ItemRabbitStew.class, new ItemRabbitStewGenerator() );
        this.generators.register( ItemRail.class, new ItemRailGenerator() );
        this.generators.register( ItemRawBeef.class, new ItemRawBeefGenerator() );
        this.generators.register( ItemRawChicken.class, new ItemRawChickenGenerator() );
        this.generators.register( ItemRawFish.class, new ItemRawFishGenerator() );
        this.generators.register( ItemRawMutton.class, new ItemRawMuttonGenerator() );
        this.generators.register( ItemRawPorkchop.class, new ItemRawPorkchopGenerator() );
        this.generators.register( ItemRawRabbit.class, new ItemRawRabbitGenerator() );
        this.generators.register( ItemRawSalmon.class, new ItemRawSalmonGenerator() );
        this.generators.register( ItemRecord11.class, new ItemRecord11Generator() );
        this.generators.register( ItemRecord13.class, new ItemRecord13Generator() );
        this.generators.register( ItemRecordBlocks.class, new ItemRecordBlocksGenerator() );
        this.generators.register( ItemRecordCat.class, new ItemRecordCatGenerator() );
        this.generators.register( ItemRecordChirp.class, new ItemRecordChirpGenerator() );
        this.generators.register( ItemRecordFar.class, new ItemRecordFarGenerator() );
        this.generators.register( ItemRecordMall.class, new ItemRecordMallGenerator() );
        this.generators.register( ItemRecordMellohi.class, new ItemRecordMellohiGenerator() );
        this.generators.register( ItemRecordStal.class, new ItemRecordStalGenerator() );
        this.generators.register( ItemRecordStrad.class, new ItemRecordStradGenerator() );
        this.generators.register( ItemRecordWait.class, new ItemRecordWaitGenerator() );
        this.generators.register( ItemRecordWard.class, new ItemRecordWardGenerator() );
        this.generators.register( ItemRedGlazedTerracotta.class, new ItemRedGlazedTerracottaGenerator() );
        this.generators.register( ItemRedMushroom.class, new ItemRedMushroomGenerator() );
        this.generators.register( ItemRedMushroomBlock.class, new ItemRedMushroomBlockGenerator() );
        this.generators.register( ItemRedNetherBrick.class, new ItemRedNetherBrickGenerator() );
        this.generators.register( ItemRedSandstone.class, new ItemRedSandstoneGenerator() );
        this.generators.register( ItemRedSandstoneSlab.class, new ItemRedSandstoneSlabGenerator() );
        this.generators.register( ItemRedSandstoneStairs.class, new ItemRedSandstoneStairsGenerator() );
        this.generators.register( ItemRedstone.class, new ItemRedstoneGenerator() );
        this.generators.register( ItemRedstoneComparator.class, new ItemRedstoneComparatorGenerator() );
        this.generators.register( ItemRedstoneComparatorPowered.class, new ItemRedstoneComparatorPoweredGenerator() );
        this.generators.register( ItemRedstoneComparatorUnpowered.class, new ItemRedstoneComparatorUnpoweredGenerator() );
        this.generators.register( ItemRedstoneLampActive.class, new ItemRedstoneLampActiveGenerator() );
        this.generators.register( ItemRedstoneLampInactive.class, new ItemRedstoneLampInactiveGenerator() );
        this.generators.register( ItemRedstoneOre.class, new ItemRedstoneOreGenerator() );
        this.generators.register( ItemRedstoneRepeater.class, new ItemRedstoneRepeaterGenerator() );
        this.generators.register( ItemRedstoneRepeaterActive.class, new ItemRedstoneRepeaterActiveGenerator() );
        this.generators.register( ItemRedstoneRepeaterInactive.class, new ItemRedstoneRepeaterInactiveGenerator() );
        this.generators.register( ItemRedstoneTorchActive.class, new ItemRedstoneTorchActiveGenerator() );
        this.generators.register( ItemRedstoneTorchInactive.class, new ItemRedstoneTorchInactiveGenerator() );
        this.generators.register( ItemRedstoneWire.class, new ItemRedstoneWireGenerator() );
        this.generators.register( ItemReeds.class, new ItemReedsGenerator() );
        this.generators.register( ItemRepeatingCommandBlock.class, new ItemRepeatingCommandBlockGenerator() );
        this.generators.register( ItemReserved6.class, new ItemReserved6Generator() );
        this.generators.register( ItemRottenFlesh.class, new ItemRottenFleshGenerator() );
        this.generators.register( ItemSaddle.class, new ItemSaddleGenerator() );
        this.generators.register( ItemSand.class, new ItemSandGenerator() );
        this.generators.register( ItemSandstone.class, new ItemSandstoneGenerator() );
        this.generators.register( ItemSandstoneStairs.class, new ItemSandstoneStairsGenerator() );
        this.generators.register( ItemSapling.class, new ItemSaplingGenerator() );
        this.generators.register( ItemSeaLantern.class, new ItemSeaLanternGenerator() );
        this.generators.register( ItemSeeds.class, new ItemSeedsGenerator() );
        this.generators.register( ItemShears.class, new ItemShearsGenerator() );
        this.generators.register( ItemShulkerBox.class, new ItemShulkerBoxGenerator() );
        this.generators.register( ItemShulkerShell.class, new ItemShulkerShellGenerator() );
        this.generators.register( ItemSign.class, new ItemSignGenerator() );
        this.generators.register( ItemSilverGlazedTerracotta.class, new ItemSilverGlazedTerracottaGenerator() );
        this.generators.register( ItemSkull.class, new ItemSkullGenerator() );
        this.generators.register( ItemSkullBlock.class, new ItemSkullBlockGenerator() );
        this.generators.register( ItemSlimeball.class, new ItemSlimeballGenerator() );
        this.generators.register( ItemSlimeBlock.class, new ItemSlimeBlockGenerator() );
        this.generators.register( ItemSnow.class, new ItemSnowGenerator() );
        this.generators.register( ItemSnowball.class, new ItemSnowballGenerator() );
        this.generators.register( ItemSnowLayer.class, new ItemSnowLayerGenerator() );
        this.generators.register( ItemSoulSand.class, new ItemSoulSandGenerator() );
        this.generators.register( ItemSpawnEgg.class, new ItemSpawnEggGenerator() );
        this.generators.register( ItemSpiderEye.class, new ItemSpiderEyeGenerator() );
        this.generators.register( ItemSplashPotion.class, new ItemSplashPotionGenerator() );
        this.generators.register( ItemSponge.class, new ItemSpongeGenerator() );
        this.generators.register( ItemSpruceDoor.class, new ItemSpruceDoorGenerator() );
        this.generators.register( ItemSpruceDoorBlock.class, new ItemSpruceDoorBlockGenerator() );
        this.generators.register( ItemSpruceFenceGate.class, new ItemSpruceFenceGateGenerator() );
        this.generators.register( ItemSpruceWoodStairs.class, new ItemSpruceWoodStairsGenerator() );
        this.generators.register( ItemStainedGlass.class, new ItemStainedGlassGenerator() );
        this.generators.register( ItemStainedGlassPane.class, new ItemStainedGlassPaneGenerator() );
        this.generators.register( ItemStainedHardenedClay.class, new ItemStainedHardenedClayGenerator() );
        this.generators.register( ItemStandingBanner.class, new ItemStandingBannerGenerator() );
        this.generators.register( ItemStandingSign.class, new ItemStandingSignGenerator() );
        this.generators.register( ItemStationaryLava.class, new ItemStationaryLavaGenerator() );
        this.generators.register( ItemStationaryWater.class, new ItemStationaryWaterGenerator() );
        this.generators.register( ItemStick.class, new ItemStickGenerator() );
        this.generators.register( ItemStickyPiston.class, new ItemStickyPistonGenerator() );
        this.generators.register( ItemStone.class, new ItemStoneGenerator() );
        this.generators.register( ItemStoneAxe.class, new ItemStoneAxeGenerator() );
        this.generators.register( ItemStoneBrick.class, new ItemStoneBrickGenerator() );
        this.generators.register( ItemStoneBrickStairs.class, new ItemStoneBrickStairsGenerator() );
        this.generators.register( ItemStoneButton.class, new ItemStoneButtonGenerator() );
        this.generators.register( ItemStonecutter.class, new ItemStonecutterGenerator() );
        this.generators.register( ItemStoneHoe.class, new ItemStoneHoeGenerator() );
        this.generators.register( ItemStonePickaxe.class, new ItemStonePickaxeGenerator() );
        this.generators.register( ItemStonePressurePlate.class, new ItemStonePressurePlateGenerator() );
        this.generators.register( ItemStoneShovel.class, new ItemStoneShovelGenerator() );
        this.generators.register( ItemStoneSlab.class, new ItemStoneSlabGenerator() );
        this.generators.register( ItemStoneSword.class, new ItemStoneSwordGenerator() );
        this.generators.register( ItemString.class, new ItemStringGenerator() );
        this.generators.register( ItemStripped1.class, new ItemStripped1Generator() );
        this.generators.register( ItemStripped2.class, new ItemStripped2Generator() );
        this.generators.register( ItemStripped3.class, new ItemStripped3Generator() );
        this.generators.register( ItemStripped4.class, new ItemStripped4Generator() );
        this.generators.register( ItemStripped5.class, new ItemStripped5Generator() );
        this.generators.register( ItemStripped6.class, new ItemStripped6Generator() );
        this.generators.register( ItemStripped7.class, new ItemStripped7Generator() );
        this.generators.register( ItemStripped8.class, new ItemStripped8Generator() );
        this.generators.register( ItemStripped9.class, new ItemStripped9Generator() );
        this.generators.register( ItemSugar.class, new ItemSugarGenerator() );
        this.generators.register( ItemSugarCane.class, new ItemSugarCaneGenerator() );
        this.generators.register( ItemSunflower.class, new ItemSunflowerGenerator() );
        this.generators.register( ItemTallGrass.class, new ItemTallGrassGenerator() );
        this.generators.register( ItemTNT.class, new ItemTNTGenerator() );
        this.generators.register( ItemTorch.class, new ItemTorchGenerator() );
        this.generators.register( ItemTotemOfUndying.class, new ItemTotemOfUndyingGenerator() );
        this.generators.register( ItemTrapdoor.class, new ItemTrapdoorGenerator() );
        this.generators.register( ItemTrappedChest.class, new ItemTrappedChestGenerator() );
        this.generators.register( ItemTripwire.class, new ItemTripwireGenerator() );
        this.generators.register( ItemTripwireHook.class, new ItemTripwireHookGenerator() );
        this.generators.register( ItemUndyedShulkerBox.class, new ItemUndyedShulkerBoxGenerator() );
        this.generators.register( ItemUpdateGameBlockUpdate1.class, new ItemUpdateGameBlockUpdate1Generator() );
        this.generators.register( ItemUpdateGameBlockUpdate2.class, new ItemUpdateGameBlockUpdate2Generator() );
        this.generators.register( ItemVines.class, new ItemVinesGenerator() );
        this.generators.register( ItemWallBanner.class, new ItemWallBannerGenerator() );
        this.generators.register( ItemWallSign.class, new ItemWallSignGenerator() );
        this.generators.register( ItemWheat.class, new ItemWheatGenerator() );
        this.generators.register( ItemWhiteGlazedTerracotta.class, new ItemWhiteGlazedTerracottaGenerator() );
        this.generators.register( ItemWood.class, new ItemWoodGenerator() );
        this.generators.register( ItemWoodenAxe.class, new ItemWoodenAxeGenerator() );
        this.generators.register( ItemWoodenButton.class, new ItemWoodenButtonGenerator() );
        this.generators.register( ItemWoodenDoorBlock.class, new ItemWoodenDoorBlockGenerator() );
        this.generators.register( ItemWoodenDoubleSlab.class, new ItemWoodenDoubleSlabGenerator() );
        this.generators.register( ItemWoodenHoe.class, new ItemWoodenHoeGenerator() );
        this.generators.register( ItemWoodenPickaxe.class, new ItemWoodenPickaxeGenerator() );
        this.generators.register( ItemWoodenPressurePlate.class, new ItemWoodenPressurePlateGenerator() );
        this.generators.register( ItemWoodenShovel.class, new ItemWoodenShovelGenerator() );
        this.generators.register( ItemWoodenSlab.class, new ItemWoodenSlabGenerator() );
        this.generators.register( ItemWoodenSword.class, new ItemWoodenSwordGenerator() );
        this.generators.register( ItemWool.class, new ItemWoolGenerator() );
        this.generators.register( ItemWritableBook.class, new ItemWritableBookGenerator() );
        this.generators.register( ItemWrittenBook.class, new ItemWrittenBookGenerator() );
        this.generators.register( ItemYellowGlazedTerracotta.class, new ItemYellowGlazedTerracottaGenerator() );
    }

    /**
     * Create a new item stack based on a id
     *
     * @param id     of the type for this item stack
     * @param data   for this item stack
     * @param amount in this item stack
     * @param nbt    additional data for this item stack
     * @param <T>    type of item stack
     * @return generated item stack
     */
    public <T extends ItemStack> T create( int id, short data, byte amount, NBTTagCompound nbt ) {
        ItemGenerator itemGenerator = this.generators.getGenerator( id );
        if ( itemGenerator == null ) {
            LOGGER.warn( "Unknown item {}", id );
            return null;
        }

        // Cleanup NBT tag, root must be empty string
        if ( nbt != null && !nbt.getName().isEmpty() ) {
            nbt = nbt.deepClone( "" );
        }

        return itemGenerator.generate( data, amount, nbt );
    }

    /**
     * Create a new item stack based on a api interface
     *
     * @param itemClass which defines what item to use
     * @param amount    in this item stack
     * @param <T>       type of item stack
     * @return generated item stack
     */
    public <T extends ItemStack> T create( Class<T> itemClass, byte amount ) {
        ItemGenerator itemGenerator = this.generators.getGenerator( itemClass );
        if ( itemGenerator == null ) {
            return null;
        }

        return itemGenerator.generate( (short) 0, amount );
    }

}
