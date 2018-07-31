/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.entity.EntityDamageByEntityEvent;
import io.gomint.event.entity.EntityDamageEvent;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityDamageListener implements EventListener {

    @EventHandler
    public void onEntityDamage( EntityDamageEvent event ) {
        event.setCancelled( true );
    }

    @EventHandler
    public void onEntityFromOtherDamage( EntityDamageByEntityEvent event ) {
        event.setCancelled( true );
    }

}
