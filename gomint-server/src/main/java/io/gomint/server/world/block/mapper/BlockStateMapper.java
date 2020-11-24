/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.mapper;

import java.util.Map;

public interface BlockStateMapper {

    /**
     * Map the block state to their runtime id
     *
     * @param state of this block
     * @return runtime id
     */
    int map(Map<String, Object> state);

}
