package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockWall;
import io.gomint.world.block.data.ConnectionType;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.data.StoneType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall")
@RegisterInfo(sId = "minecraft:blackstone_wall")
@RegisterInfo(sId = "minecraft:polished_blackstone_wall")
@RegisterInfo(sId = "minecraft:polished_blackstone_brick_wall")
public class Wall extends Block implements BlockWall {

    private static final String STONE_SLAB_ID = "minecraft:cobblestone_wall";
    private static final String STONE_TYPE = "wall_block_type";

    private enum ConnectionTypeMagic {

        SHORT("short"),
        TALL("tall"),
        NONE("none");

        private final String type;

        ConnectionTypeMagic(String type) {
            this.type = type;
        }

    }

    private enum StoneTypeMagic {

        // Slab types 1
        SMOOTH_STONE("", "", ""),
        SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "sandstone"),
        COBBLESTONE(STONE_SLAB_ID, STONE_TYPE, "cobblestone"),
        BRICK("", "", ""),
        STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "stone_brick"),
        QUARTZ("", "", ""),
        NETHER_BRICK(STONE_SLAB_ID, STONE_TYPE, "nether_brick"),

        // Slab types 2
        RED_SANDSTONE(STONE_SLAB_ID, STONE_TYPE, "red_sandstone"),
        PURPUR("", "", ""),
        PRISMARINE_ROUGH(STONE_SLAB_ID, STONE_TYPE, "prismarine"),
        PRISMARINE_DARK("", "", ""),
        PRISMARINE_BRICK(STONE_SLAB_ID, STONE_TYPE, ""),
        MOSSY_COBBLESTONE(STONE_SLAB_ID, STONE_TYPE, "mossy_cobblestone"),
        SMOOTH_SANDSTONE("", "", ""),
        RED_NETHER_BRICK(STONE_SLAB_ID, STONE_TYPE, "red_nether_brick"),

        // Slab types 3
        END_STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "end_brick"),
        SMOOTH_RED_SANDSTONE("", "", ""),
        POLISHED_ANDESITE("", "", ""),
        ANDESITE(STONE_SLAB_ID, STONE_TYPE, "andesite"),
        DIORITE(STONE_SLAB_ID, STONE_TYPE, "diorite"),
        POLISHED_DIORITE("", "", ""),
        GRANITE(STONE_SLAB_ID, STONE_TYPE, "granite"),
        POLISHED_GRANITE("", "", ""),

        // Slab types 4
        MOSSY_STONE_BRICK(STONE_SLAB_ID, STONE_TYPE, "mossy_stone_brick"),
        SMOOTH_QUARTZ("", "", ""),
        STONE("", "", ""),
        CUT_SANDSTONE("", "", ""),
        CUT_RED_STONE("", "", ""),

        // Additional slabs (new ones)
        BLACKSTONE("minecraft:blackstone_wall", "", ""),
        POLISHED_BLACKSTONE("minecraft:polished_blackstone_wall", "", ""),
        POLISHED_BLACKSTONE_BRICK("minecraft:polished_blackstone_brick_wall", "", "");

        private final String key;
        private final String value;
        private final String blockId;

        StoneTypeMagic(String blockId, String key, String value) {
            this.key = key;
            this.value = value;
            this.blockId = blockId;
        }

    }

    private static final EnumBlockState<StoneTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        return new String[]{STONE_TYPE};
    }, StoneTypeMagic.values(), v -> v.value, v -> {
        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });
    private static final BooleanBlockState POST = new BooleanBlockState(() -> new String[]{"wall_post_bit"});

    // Connection types
    private static final EnumBlockState<ConnectionTypeMagic, String> SOUTH = new EnumBlockState<>(v -> new String[]{"wall_connection_type_south"}, ConnectionTypeMagic.values(), v -> v.type, s -> {
        for (ConnectionTypeMagic value : ConnectionTypeMagic.values()) {
            if (value.type.equals(s)) {
                return value;
            }
        }

        return null;
    });

    private static final EnumBlockState<ConnectionTypeMagic, String> WEST = new EnumBlockState<>(v -> new String[]{"wall_connection_type_west"}, ConnectionTypeMagic.values(), v -> v.type, s -> {
        for (ConnectionTypeMagic value : ConnectionTypeMagic.values()) {
            if (value.type.equals(s)) {
                return value;
            }
        }

        return null;
    });

    private static final EnumBlockState<ConnectionTypeMagic, String> NORTH = new EnumBlockState<>(v -> new String[]{"wall_connection_type_north"}, ConnectionTypeMagic.values(), v -> v.type, s -> {
        for (ConnectionTypeMagic value : ConnectionTypeMagic.values()) {
            if (value.type.equals(s)) {
                return value;
            }
        }

        return null;
    });

    private static final EnumBlockState<ConnectionTypeMagic, String> EAST = new EnumBlockState<>(v -> new String[]{"wall_connection_type_east"}, ConnectionTypeMagic.values(), v -> v.type, s -> {
        for (ConnectionTypeMagic value : ConnectionTypeMagic.values()) {
            if (value.type.equals(s)) {
                return value;
            }
        }

        return null;
    });

    private void update() {
        // Check if we have others around and update connections if needed
        for (Direction value : Direction.values()) {
            Block block = this.side(value);

            connection(value, ConnectionType.NONE);
            if (block.solid()) {
                connection(value, ConnectionType.SHORT);
            }
        }

        // Check if we need the pole
        this.pole(true);
        if (this.connection(Direction.NORTH) == ConnectionType.SHORT &&
            this.connection(Direction.SOUTH) == ConnectionType.SHORT) {
            this.pole(this.connection(Direction.WEST) != ConnectionType.NONE ||
                this.connection(Direction.EAST) != ConnectionType.NONE);
        } else if (this.connection(Direction.WEST) == ConnectionType.SHORT &&
            this.connection(Direction.EAST) == ConnectionType.SHORT) {
            this.pole(this.connection(Direction.SOUTH) != ConnectionType.NONE ||
                this.connection(Direction.NORTH) != ConnectionType.NONE);
        }
    }

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.WALL;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        return Collections.singletonList(new AxisAlignedBB(
            this.location.x() + 0.25f,
            this.location.y(),
            this.location.z() + 0.25f,
            this.location.x() + 0.75f,
            this.location.y() + 1,
            this.location.z() + 0.75f
        ));
    }

    @Override
    public StoneType type() {
        switch (this.getBlockId()) {
            case "minecraft:blackstone_wall":
                return StoneType.BLACKSTONE;
            case "minecraft:polished_blackstone_wall":
                return StoneType.POLISHED_BLACKSTONE;
            case "minecraft:polished_blackstone_brick_wall":
                return StoneType.POLISHED_BLACKSTONE_BRICK;
        }

        return StoneType.valueOf(VARIANT.getState(this).name());
    }

    @Override
    public BlockWall type(StoneType stoneType) {
        StoneTypeMagic newState = StoneTypeMagic.valueOf(stoneType.name());
        if (newState.blockId.isEmpty()) {
            return this;
        }

        this.setBlockId(newState.blockId);
        VARIANT.setState(this, newState);
        return this;
    }

    @Override
    public boolean pole() {
        return POST.getState(this);
    }

    @Override
    public BlockWall pole(boolean pole) {
        POST.setState(this, pole);
        return this;
    }

    @Override
    public BlockWall connection(Direction direction, ConnectionType connectionType) {
        ConnectionTypeMagic state = ConnectionTypeMagic.valueOf(connectionType.name());
        switch (direction) {
            case SOUTH:
                SOUTH.setState(this, state);
                break;

            case EAST:
                EAST.setState(this, state);
                break;

            case WEST:
                WEST.setState(this, state);
                break;

            case NORTH:
                NORTH.setState(this, state);
                break;
        }

        return this;
    }

    @Override
    public ConnectionType connection(Direction direction) {
        switch (direction) {
            case SOUTH:
                return ConnectionType.valueOf(SOUTH.getState(this).name());

            case EAST:
                return ConnectionType.valueOf(EAST.getState(this).name());

            case WEST:
                return ConnectionType.valueOf(WEST.getState(this).name());

            case NORTH:
                return ConnectionType.valueOf(NORTH.getState(this).name());
        }

        return null;
    }

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location) {
        this.update();
        return super.beforePlacement(entity, item, face, location);
    }

    @Override
    public long update(UpdateReason updateReason, long currentTimeMS, float dT) {
        if (updateReason == UpdateReason.NEIGHBOUR_UPDATE) {
            this.update();
        }

        return super.update(updateReason, currentTimeMS, dT);
    }

}
