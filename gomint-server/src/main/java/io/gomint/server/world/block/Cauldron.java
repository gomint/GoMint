package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.tileentity.CauldronTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockCauldron;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.data.LiquidType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cauldron", def = true)
@RegisterInfo(sId = "minecraft:lava_cauldron")
public class Cauldron extends Block implements BlockCauldron {

    private static final String[] FILL_LEVEL_KEY = new String[]{"fill_level"};
    private static final ProgressBlockState FILL_LEVEL = new ProgressBlockState(() -> FILL_LEVEL_KEY, 6, aVoid -> {});
    private static final String[] LIQUID_KEY = new String[]{"cauldron_liquid"};
    private static final EnumBlockState<LiquidTypeMagic, String> LIQUID = new EnumBlockState<>(v -> LIQUID_KEY, LiquidTypeMagic.values(), v -> v.liquid, s -> {
        for (LiquidTypeMagic value : LiquidTypeMagic.values()) {
            if (value.liquid.equals(s)) {
                return value;
            }
        }

        return null;
    });

    private enum LiquidTypeMagic {
        WATER("minecraft:cauldron", "water"),
        LAVA("minecraft:lava_cauldron", "lava");

        private final String blockId;
        private final String liquid;
        LiquidTypeMagic(String blockId, String liquid) {
            this.blockId = blockId;
            this.liquid = liquid;
        }
    }

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        FILL_LEVEL.setState(this, 0f);
        return super.beforePlacement(entity, item, face, location);
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CAULDRON;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        super.createTileEntity(compound);
        return this.tileEntities.construct(CauldronTileEntity.class, compound, this, this.items);
    }

    @Override
    public LiquidType type() {
        return LiquidType.valueOf(LIQUID.getState(this).name());
    }

    @Override
    public BlockCauldron type(LiquidType type) {
        LiquidTypeMagic newState = LiquidTypeMagic.valueOf(type.name());
        this.setBlockId(newState.blockId);
        LIQUID.setState(this, newState);
        return this;
    }

    @Override
    public float fillHeight() {
        return FILL_LEVEL.getState(this);
    }

    @Override
    public BlockCauldron fillHeight(float height) {
        if (height < 0f || height > 1f) {
            return this;
        }

        FILL_LEVEL.setState(this, height);
        return this;
    }

}
