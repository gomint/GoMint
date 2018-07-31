package io.gomint.plugin.listener;

import io.gomint.entity.passive.EntityCow;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.EventPriority;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.inventory.item.ItemArrow;
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

        event.getPlayer().getInventory().setItem( 0, ItemArrow.create( 12 ) );

        // Spawn a entity human in front
        EntityCow entityHuman = EntityCow.create();
        entityHuman.setNameTag( "TEST" );
        entityHuman.spawn( event.getPlayer().getSpawnLocation().add( new Vector( 2, 0, 2 ) ) );

        this.plugin.getScheduler().schedule( () -> entityHuman.setNameTag( "TEST:" + FastRandom.current().nextInt( 1000 ) ), 5, 5, TimeUnit.SECONDS );
    }

}
