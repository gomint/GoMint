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
public abstract class Wall<B extends io.gomint.world.block.Block> extends Block implements BlockWall<B> {

    private enum ConnectionTypeMagic {

        SHORT("short"),
        TALL("tall"),
        NONE("none");

        private final String type;

        ConnectionTypeMagic(String type) {
            this.type = type;
        }

    }

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
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
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
    public boolean pole() {
        return POST.state(this);
    }

    @Override
    public B pole(boolean pole) {
        POST.state(this, pole);
        return (B) this;
    }

    @Override
    public B connection(Direction direction, ConnectionType connectionType) {
        ConnectionTypeMagic state = ConnectionTypeMagic.valueOf(connectionType.name());
        switch (direction) {
            case SOUTH:
                SOUTH.state(this, state);
                break;

            case EAST:
                EAST.state(this, state);
                break;

            case WEST:
                WEST.state(this, state);
                break;

            case NORTH:
                NORTH.state(this, state);
                break;
        }

        return (B) this;
    }

    @Override
    public ConnectionType connection(Direction direction) {
        switch (direction) {
            case SOUTH:
                return ConnectionType.valueOf(SOUTH.state(this).name());

            case EAST:
                return ConnectionType.valueOf(EAST.state(this).name());

            case WEST:
                return ConnectionType.valueOf(WEST.state(this).name());

            case NORTH:
                return ConnectionType.valueOf(NORTH.state(this).name());
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
