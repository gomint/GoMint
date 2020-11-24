/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.world.block.data.RotationDirection;
import io.gomint.world.block.data.SignDirection;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SignDirectionBlockState extends EnumBlockState<SignDirection, Integer> {

    public SignDirectionBlockState(Supplier<String[]> keys) {
        super(v -> keys.get(), SignDirection.values(), v -> v.ordinal(), v -> SignDirection.values()[v]);
    }

}
