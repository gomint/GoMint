package io.gomint.plugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.EventPriority;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.plugin.TestPlugin;
import io.gomint.world.Gamemode;
import lombok.RequiredArgsConstructor;

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
<<<<<<< HEAD

        event.getPlayer().getInventory().setItem( 0, ItemStrippedAcaciaLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 1, ItemStrippedBirchLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 2, ItemStrippedDarkOakLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 3, ItemStrippedJungleLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 4, ItemStrippedOakLog.create( 1 ) );
        event.getPlayer().getInventory().setItem( 5, ItemStrippedSpruceLog.create( 1 ) );

        // Spawn a entity human in front
        this.plugin.getScheduler().schedule( () -> plugin.getLogger().info( "Location {}", event.getPlayer().getLocation() ), 1, 1, TimeUnit.SECONDS );
=======
        event.getPlayer().setGamemode( Gamemode.CREATIVE );
>>>>>>> 893927c5c519bac56ae7d9e5f6a224f821340990
    }

}
