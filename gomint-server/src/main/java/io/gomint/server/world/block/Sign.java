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
public abstract class Sign<B> extends Block implements BlockSign<B> {

    @Override
    public long breakTime() {
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
    public float blastResistance() {
        return 5.0f;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public List<String> lines() {
        SignTileEntity sign = this.tileEntity();
        return sign == null ? null : new ArrayList<>(sign.getLines());
    }

    @Override
    public B line(int line, String content) {
        // Silently fail when line is incorrect
        if (line > 4 || line < 1) {
            return (B) this;
        }

        SignTileEntity sign = this.tileEntity();
        if (sign == null) {
            return (B) this;
        }

        if (sign.getLines().size() < line) {
            for (int i = 0; i < line - sign.getLines().size(); i++) {
                sign.getLines().add("");
            }
        }

        sign.getLines().set(line - 1, content);
        this.updateBlock();
        return (B) this;
    }

    @Override
    public String line(int line) {
        // Silently fail when line is incorrect
        if (line > 4 || line < 1) {
            return null;
        }

        SignTileEntity sign = this.tileEntity();
        if (sign == null) {
            return null;
        }

        return sign.getLines().size() < line ? null : sign.getLines().get(line - 1);
    }

}
