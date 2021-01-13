package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockSign;

import java.util.ArrayList;
import java.util.List;

/**
 * @author derklaro
 * @version 1.0
 */
public abstract class Sign extends Block implements BlockSign {

    @Override
    public long getBreakTime() {
        return 1500;
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
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    protected TileEntity createTileEntity(NBTTagCompound compound) {
        return this.tileEntities.construct(SignTileEntity.class, compound, this, this.items);
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public List<String> getLines() {
        SignTileEntity sign = this.getTileEntity();
        return sign == null ? null : new ArrayList<>(sign.getLines());
    }

    @Override
    public void setLine(int line, String content) {
        // Silently fail when line is incorrect
        if (line > 4 || line < 1) {
            return;
        }

        SignTileEntity sign = this.getTileEntity();
        if (sign == null) {
            return;
        }

        if (sign.getLines().size() < line) {
            for (int i = 0; i < line - sign.getLines().size(); i++) {
                sign.getLines().add("");
            }
        }

        sign.getLines().set(line - 1, content);
        this.updateBlock();
    }

    @Override
    public String getLine(int line) {
        // Silently fail when line is incorrect
        if (line > 4 || line < 1) {
            return null;
        }

        SignTileEntity sign = this.getTileEntity();
        if (sign == null) {
            return null;
        }

        return sign.getLines().size() < line ? null : sign.getLines().get(line - 1);
    }
}
