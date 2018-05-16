package io.gomint.server.world.block.helper;

import io.gomint.inventory.item.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ToolPresets {

    public static final Class[] AXE = new Class[]{
        ItemDiamondAxe.class,
        ItemGoldenAxe.class,
        ItemIronAxe.class,
        ItemStoneAxe.class,
        ItemWoodenAxe.class
    };

    public static final Class[] PICKAXE = new Class[]{
        ItemDiamondPickaxe.class,
        ItemGoldenPickaxe.class,
        ItemIronPickaxe.class,
        ItemStonePickaxe.class,
        ItemWoodenPickaxe.class
    };

    public static final Class[] SHOVEL = new Class[]{
        ItemWoodenShovel.class,
        ItemIronShovel.class,
        ItemDiamondShovel.class,
        ItemGoldenShovel.class,
        ItemStoneShovel.class
    };

}
