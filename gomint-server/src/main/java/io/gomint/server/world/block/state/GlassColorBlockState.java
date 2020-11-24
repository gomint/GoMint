/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.world.block.data.GlassColor;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class GlassColorBlockState extends EnumBlockState<GlassColor, String> {

    public GlassColorBlockState(Supplier<String[]> keys) {
        super(v -> keys.get(), GlassColor.values(), e -> e.name().toLowerCase(), v -> GlassColor.valueOf(v.toUpperCase()) );
    }

}
