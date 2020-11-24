/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.passive;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.network.packet.PacketUpdateBlock;
import io.gomint.server.network.packet.PacketUpdateBlockSynched;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:falling_block")
public class EntityFallingBlock extends Entity implements io.gomint.entity.passive.EntityFallingBlock {

    private Block block;

    /**
     * Constructs a new EntityFallingBlock
     *
     * @param block Which will be represented by this entity
     * @param world The world in which this entity is in
     */
    public EntityFallingBlock(Block block, WorldAdapter world) {
        super(EntityType.FALLING_BLOCK, world);
        this.initEntity();
        this.setBlock(block);
    }

    /**
     * Create new entity falling block for API
     */
    public EntityFallingBlock() {
        super(EntityType.FALLING_BLOCK, null);
        this.initEntity();
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        if (this.isDead()) {
            return;
        }

        super.update(currentTimeMS, dT);

        // Are we onground?
        if (this.onGround) {
            this.despawn();

            // Check if block can be replaced
            Block block = this.world.getBlockAt(this.getLocation().add(-(this.getWidth() / 2), this.getHeight(), -(this.getWidth() / 2)).toBlockPosition());
            if ( block.canBeReplaced( null ) ) {
                block.copyFromBlock(this.block);
            } else {
                // Generate new item drop
                for (ItemStack drop : this.block.getDrops(null)) {
                    this.world.dropItem(this.getLocation(), drop);
                }
            }
        }
    }

    @Override
    protected void fall() {
        // We don't need fall damage here
        this.fallDistance = 0;
    }

    private void initEntity() {
        this.setSize(0.98f, 0.98f);
        this.offsetY = 0.49f;

        GRAVITY = 0.04f;
        DRAG = 0.02f;
    }

    @Override
    public void setBlock(io.gomint.world.block.Block block) {
        Block block1 = (Block) block;

        this.block = block1;
        this.metadataContainer.putInt(MetadataContainer.DATA_VARIANT, block1.getRuntimeId());
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.PASSIVE;
    }

}
