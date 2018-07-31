package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemReduceBreaktime;
import io.gomint.server.inventory.item.annotation.UseDataAsDamage;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@UseDataAsDamage
public abstract class ItemReduceTierGolden extends ItemStack implements ItemReduceBreaktime {



    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public float getDivisor() {
        return 16;
    }

    @Override
    public short getMaxDamage() {
        return 33;
    }

}
