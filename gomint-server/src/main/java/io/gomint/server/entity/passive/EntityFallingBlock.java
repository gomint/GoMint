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
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:falling_block")
public class EntityFallingBlock extends Entity<io.gomint.entity.passive.EntityFallingBlock> implements io.gomint.entity.passive.EntityFallingBlock {

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
        this.block(block);
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
        if (this.dead()) {
            return;
        }

        super.update(currentTimeMS, dT);

        // Are we onground?
        if (this.onGround) {
            this.despawn();

            // Check if block can be replaced
            Block block = this.world.blockAt(this.location().add(-(this.width() / 2), this.height(), -(this.width() / 2)).toBlockPosition());
            if ( block.canBeReplaced( null ) ) {
                block.copyFromBlock(this.block);
            } else {
                // Generate new item drop
                for (ItemStack<?> drop : this.block.drops(null)) {
                    this.world.dropItem(this.location(), drop);
                }
            }
        }
    }

    @Override
    protected EntityFallingBlock fall() {
        // We don't need fall damage here
        this.fallDistance = 0;
        return this;
    }

    private void initEntity() {
        this.size(0.98f, 0.98f);
        this.offsetY = 0.49f;

        this.gravity = 0.04f;
        this.drag = 0.02f;
    }

    @Override
    public EntityFallingBlock block(io.gomint.world.block.Block block) {
        Block block1 = (Block) block;

        this.block = block1;
        this.metadataContainer.putInt(MetadataContainer.DATA_VARIANT, block1.runtimeId());
        return this;
    }

    @Override
    public Set<String> tags() {
        return EntityTags.PASSIVE;
    }

}
