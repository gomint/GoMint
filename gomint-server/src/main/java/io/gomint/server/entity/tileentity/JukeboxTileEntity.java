package io.gomint.server.entity.tileentity;

import io.gomint.server.entity.component.ItemComponent;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Jukebox")
public class JukeboxTileEntity extends TileEntity {

    private final ItemComponent itemComponent;

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public JukeboxTileEntity(Block block, Items items) {
        super(block, items);
        this.itemComponent = new ItemComponent(this, items, "RecordItem");
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);
        this.itemComponent.fromCompound(compound);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    public void setRecordItem(ItemStack<?> recordItem) {
        this.itemComponent.setItem(recordItem);
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "Jukebox");
        this.itemComponent.toCompound(compound, reason);
    }

}
