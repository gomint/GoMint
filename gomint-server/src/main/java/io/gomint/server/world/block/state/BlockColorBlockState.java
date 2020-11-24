/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.world.block.data.BlockColor;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockColorBlockState extends EnumBlockState<BlockColor, String> {

    public BlockColorBlockState(Supplier<String[]> keys) {
        super(v -> keys.get(), BlockColor.values(), e -> e.name().toLowerCase(), v -> BlockColor.valueOf(v.toUpperCase()) );
    }

}
