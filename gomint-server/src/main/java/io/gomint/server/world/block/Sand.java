package io.gomint.server.world.block;

import com.google.common.collect.Lists;
import io.gomint.inventory.item.data.SandType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockSand;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sand")
public class Sand extends Fallable implements BlockSand {

    private static final String[] SAND_TYPE = new String[]{"sand_type"};

    private enum SandTypeMagic {
        RED("red"),
        NORMAL("normal");

        private final String type;

        SandTypeMagic(String type) {
            this.type = type;
        }
    }

    private static final EnumBlockState<SandTypeMagic, String> TYPE = new EnumBlockState<>(newValue -> {
        return SAND_TYPE;
    }, SandTypeMagic.values(), v -> v.type, v -> {
        for (SandTypeMagic value : SandTypeMagic.values()) {
            if (value.type.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.SHOVEL;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SAND;
    }

    @Override
    public void setType(SandType type) {
        SandTypeMagic newState = SandTypeMagic.valueOf(type.name());
        TYPE.setState(this, newState);
    }

    @Override
    public SandType getType() {
        return SandType.valueOf(TYPE.getState(this).name());
    }

    @Override
    public PlacementData calculatePlacementData(EntityPlayer entity, ItemStack item, Facing face, Block block, Block clickedBlock, Vector clickVector) {
        PlacementData placementData = super.calculatePlacementData(entity, item, face, block, clickedBlock, clickVector);
        BlockIdentifier identifier = placementData.getBlockIdentifier();

        SandTypeMagic should = item == null || ((io.gomint.server.inventory.item.ItemStack) item).getData() == 0 ? SandTypeMagic.NORMAL : SandTypeMagic.RED;
        placementData.setBlockIdentifier(BlockRuntimeIDs.change(identifier, null, SAND_TYPE, should.type));
        return placementData;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        if (TYPE.getState(this) == SandTypeMagic.NORMAL) {
            ItemStack drop = this.world.getServer().getItems().create(this.identifier.getBlockId(), (short) 0, (byte) 1, null);
            return Lists.newArrayList(drop);
        }

        ItemStack drop = this.world.getServer().getItems().create(this.identifier.getBlockId(), (short) 1, (byte) 1, null);
        return Lists.newArrayList(drop);
    }

}
