/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.testplugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerRespawnEvent;
import io.gomint.inventory.item.ItemDiamond;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerRespawnListener implements EventListener {

    @EventHandler
    public void onRespawn( PlayerRespawnEvent event ) {
        event.player().inventory().clear();
        event.player().inventory().item( 0, ItemDiamond.create( 1 ) );
    }

}
