/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.tick;

import io.gomint.math.MathUtils;
import io.gomint.server.util.Values;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ClientTickable implements Tickable {

    private float lastUpdateDT;

    @Override
    public void update(long currentTimeMS, float dT) {
        this.lastUpdateDT += dT;
        if (Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON) {
            this.updateClientTick();
            this.lastUpdateDT = 0;
        }
    }

    /**
     * Get called once a client tick (50ms) has been reached
     */
    public abstract void updateClientTick();

}
