package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemLeaves;
import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockLeaves;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.LogType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:leaves", def = true )
@RegisterInfo( sId = "minecraft:leaves2" )
public class Leaves extends Block implements BlockLeaves {

    private static final String OLD_LOG_TYPE = "old_leaf_type";
    private static final String OLD_LOG_ID = "minecraft:leaves";

    private static final String NEW_LOG_TYPE = "new_leaf_type";
    private static final String NEW_LOG_ID = "minecraft:leaves2";

    @Getter
    private enum LeaveTypeMagic {
        OAK(OLD_LOG_ID, OLD_LOG_TYPE, "oak"),
        SPRUCE(OLD_LOG_ID, OLD_LOG_TYPE, "spruce"),
        BIRCH(OLD_LOG_ID, OLD_LOG_TYPE, "birch"),
        JUNGLE(OLD_LOG_ID, OLD_LOG_TYPE, "jungle"),
        ACACIA(NEW_LOG_ID, NEW_LOG_TYPE, "acacia"),
        DARK_OAK(NEW_LOG_ID, NEW_LOG_TYPE, "dark_oak");

        private final String key;
        private final String value;
        private final String blockId;

        LeaveTypeMagic(String blockId, String key, String value) {
            this.key = key;
            this.value = value;
            this.blockId = blockId;
        }
    }

    private final EnumBlockState<LeaveTypeMagic, String> variant = new EnumBlockState<>(this, () -> {
        if (this.variant == null) {
            return LeaveTypeMagic.OAK.getKey();
        }

        return this.variant.getState().getKey();
    }, LeaveTypeMagic.values(), LeaveTypeMagic::getValue);

    private final BooleanBlockState updateForDecay = new BooleanBlockState( this, () -> "update_bit" );
    private final BooleanBlockState persistent = new BooleanBlockState( this, () -> "persistent_bit");

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LEAVES;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<>() {{
            if ( isCorrectTool( itemInHand ) ) {
                add( ItemLeaves.create( 1 ) );
            }
        }};
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

    @Override
    public void setLeaveType( LogType type ) {
        LeaveTypeMagic newState = LeaveTypeMagic.valueOf(type.name());
        this.setBlockId(newState.getBlockId());
        this.variant.setState(newState);
    }

    @Override
    public LogType getLeaveType() {
        return LogType.valueOf(this.variant.getState().name());
    }

}
