package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemSapling;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockSapling;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.data.LogType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sapling")
@RegisterInfo(sId = "minecraft:crimson_fungus")
@RegisterInfo(sId = "minecraft:warped_fungus")
public class Sapling extends Block implements BlockSapling {

    private enum LogTypeMagic {
        OAK("minecraft:sapling", "oak"),
        SPRUCE("minecraft:sapling", "spruce"),
        BIRCH("minecraft:sapling", "birch"),
        JUNGLE("minecraft:sapling", "jungle"),
        ACACIA("minecraft:sapling", "acacia"),
        DARK_OAK("minecraft:sapling", "dark_oak"),
        CRIMSON("minecraft:crimson_fungus", ""),
        WARPED("minecraft:warped_fungus", "");

        private final String id;
        private final String value;

        LogTypeMagic(String id, String value) {
            this.id = id;
            this.value = value;
        }
    }

    private static final EnumBlockState<LogTypeMagic, String> TYPE = new EnumBlockState<>(v -> new String[]{"sapling_type"}, LogTypeMagic.values(),
        logTypeMagic -> logTypeMagic.value, s -> {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.value.equals(s)) {
                return value;
            }
        }

        return null;
    });

    private static final BooleanBlockState AGE = new BooleanBlockState(() -> new String[]{"age_bit"});

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location) {
        AGE.state(this, false);

        Block down = this.side(Facing.DOWN);
        return down.solid();
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SAPLING;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public BlockSapling type(LogType type) {
        TYPE.state(this, LogTypeMagic.valueOf(type.name()));
        return this;
    }

    @Override
    public LogType type() {
        return LogType.valueOf(TYPE.state(this).name());
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        ItemSapling sapling = ItemSapling.create(1);
        sapling.type(this.type());
        return Collections.singletonList(sapling);
    }

}
