package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockCobblestoneWall;
import io.gomint.world.block.BlockStoneWall;
import io.gomint.world.block.BlockWall;
import io.gomint.world.block.data.CobblestoneType;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall[0]", def = true)
@RegisterInfo(sId = "minecraft:cobblestone_wall[1]")
public class ItemCobblestoneWall extends ItemStack<io.gomint.inventory.item.ItemCobblestoneWall> implements io.gomint.inventory.item.ItemCobblestoneWall {

    private static final String WALL_ID = "minecraft:cobblestone_wall";

    private enum StoneTypeMagic {
        NORMAL(WALL_ID, (short) 0),
        MOSSY(WALL_ID, (short) 1);

        private final String wallId;
        private final short data;
        StoneTypeMagic(String wallId, short data) {
            this.wallId = wallId;
            this.data = data;
        }
    }

    @Override
    public ItemType itemType() {
        return ItemType.COBBLESTONE_WALL;
    }

    @Override
    public CobblestoneType type() {
        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (value.wallId.equals(this.material()) && value.data == this.data()) {
                return CobblestoneType.valueOf(value.name());
            }
        }

        return null;
    }

    @Override
    public ItemCobblestoneWall type(CobblestoneType stoneType) {
        StoneTypeMagic state = StoneTypeMagic.valueOf(stoneType.name());
        this.material(state.wallId);
        this.data(state.data);
        return this;
    }

    @Override
    public Block block() {
        return this.blocks
            .get(BlockCobblestoneWall.class)
            .type(this.type());
    }

}
