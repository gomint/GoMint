/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.entity.EntityExplodeEvent;
import io.gomint.world.block.Block;

import java.util.function.Predicate;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityExplodeListener implements EventListener {

    @EventHandler
    public void onEntityExplode( EntityExplodeEvent e ) {
        e.getAffectedBlocks().removeIf( new Predicate<Block>() {
            @Override
            public boolean test( Block block ) {
                switch ( block.getType() ) {
                    case STONE:
                    case CHEST:
                    case ENCHANTMENT_TABLE:
                        return false;

                    default:
                        return true;
                }
            }
        } );
    }

}
