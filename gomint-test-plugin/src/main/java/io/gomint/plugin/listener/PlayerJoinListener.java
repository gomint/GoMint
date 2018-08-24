package io.gomint.plugin.listener;

import io.gomint.entity.passive.EntityCow;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.EventPriority;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.inventory.item.ItemStrippedAcaciaLog;
import io.gomint.inventory.item.ItemStrippedBirchLog;
import io.gomint.inventory.item.ItemStrippedDarkOakLog;
import io.gomint.inventory.item.ItemStrippedJungleLog;
import io.gomint.inventory.item.ItemStrippedOakLog;
import io.gomint.inventory.item.ItemStrippedSpruceLog;
import io.gomint.math.Vector;
import io.gomint.plugin.TestPlugin;
import io.gomint.util.random.FastRandom;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class PlayerJoinListener implements EventListener {

    private final TestPlugin plugin;

    @EventHandler( priority = EventPriority.HIGHEST )
    public void onPlayerJoin( PlayerJoinEvent event ) {
        // Set to allow all permissions
        event.getPlayer().getPermissionManager().setPermission( "*", true );

        event.getPlayer().getInventory().setItem( 0, ItemStrippedAcaciaLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 1, ItemStrippedBirchLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 2, ItemStrippedDarkOakLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 3, ItemStrippedJungleLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 4, ItemStrippedOakLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 5, ItemStrippedSpruceLog.create( 1 ) );

        // Spawn a entity human in front
        this.plugin.getScheduler().schedule( () -> plugin.getLogger().info( "Location {}", event.getPlayer().getLocation() ), 1, 1, TimeUnit.SECONDS );
    }

}
