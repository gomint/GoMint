/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.world.block.data.RotationDirection;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class RotationDirectionBlockState extends EnumBlockState<RotationDirection, Integer> {

    public RotationDirectionBlockState(Supplier<String[]> keys) {
        super(v -> keys.get(), RotationDirection.values(), v -> v.ordinal(), v -> RotationDirection.values()[v]);
    }

}
