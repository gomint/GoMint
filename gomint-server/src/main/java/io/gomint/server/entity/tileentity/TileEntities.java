/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.StringRegistry;
import io.gomint.server.util.ClassPath;
import io.gomint.server.world.block.Banner;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TileEntities {

    private static final Logger LOGGER = LoggerFactory.getLogger(TileEntities.class);
    private final StringRegistry<TileEntity> generators;
    private final Items items;

    public TileEntities(ClassPath classPath, Items items) {
        this.items = items;
        this.generators = new StringRegistry<>((clazz, id) -> {
            return null;
        });

        this.generators.register(classPath, "io.gomint.server.entity.tileentity");
        Registry.register(this.generators);
    }

    /**
     * Construct a new TileEntity which then reads in the data from the given Compound
     *
     * @param compound The compound from which the data should be read
     * @param block    The block in which this TileEntity resides
     * @return The constructed and ready to use TileEntity or null
     */
    public TileEntity construct(NBTTagCompound compound, Block block) {
        // Check if compound has a id
        String id = compound.getString("id", null);
        if (id == null) {
            return null;
        }

        Generator<TileEntity> tileEntityGenerator = this.generators.getGenerator(id, 2);
        if (tileEntityGenerator != null) {
            TileEntity tileEntity = tileEntityGenerator.generate(block, this.items);
            tileEntity.fromCompound(compound);
            return tileEntity;
        }

        LOGGER.warn("Unknown tile entity found: {} -> {}", id, compound);
        return null;
    }

    public TileEntity construct(Class<? extends TileEntity> teClass, NBTTagCompound compound, Object... init) {
        Generator<TileEntity> tileEntityGenerator = this.generators.getGenerator(teClass, init.length);
        if (tileEntityGenerator != null) {
            TileEntity tileEntity = tileEntityGenerator.generate(init);
            tileEntity.fromCompound(compound);
            return tileEntity;
        }

        return null;
    }

}
