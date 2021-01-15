package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.component.InventoryComponent;
import io.gomint.server.inventory.ChestInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "ShulkerBox")
public class ShulkerBoxTileEntity extends ContainerTileEntity implements InventoryHolder {

    private final ChestInventory inventory;
    private final InventoryComponent inventoryComponent;

    private boolean undyed = true;
    private byte facing = 1;

    /**
     * Construct new tile entity
     *
     * @param block of the tile entity
     */
    public ShulkerBoxTileEntity(Block block, Items items) {
        super(block, items);
        this.inventory = new ChestInventory(items, this);
        this.inventoryComponent = new InventoryComponent(this, items, this.inventory);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        //
        this.undyed = compound.getByte("isUndyed", (byte) 1) > 0;
        this.facing = compound.getByte("facing", (byte) 1);

        // Read in items
        this.inventoryComponent.fromCompound(compound);
    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        this.inventoryComponent.interact(entity, face, facePos, item);
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "ShulkerBox");
        compound.addValue("facing", this.facing);
        compound.addValue("isUndyed", (byte) ((this.undyed) ? 1 : 0));

        this.inventoryComponent.toCompound(compound, reason);
    }

}
