package io.gomint.plugin.listener;

import io.gomint.entity.monster.EntityElderGuardian;
import io.gomint.entity.passive.EntityHuman;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.EventPriority;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.math.Vector;
import io.gomint.player.PlayerSkin;
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

        // Spawn a entity human in front
        EntityHuman entityHuman = EntityHuman.create();
        entityHuman.setSkin( PlayerSkin.empty() );
        entityHuman.setNameTag("TEST");
        entityHuman.spawn( event.getPlayer().getSpawnLocation().add( new Vector( 2, 0, 2 ) ) );

        this.plugin.getScheduler().schedule( new Runnable() {
            @Override
            public void run() {
                entityHuman.setNameTag( "TEST:" + FastRandom.current().nextInt( 1000 ) );
            }
        }, 5, 5, TimeUnit.SECONDS );

        // Spawn elder guardian
        EntityElderGuardian guardian = EntityElderGuardian.create();
        guardian.setTarget( event.getPlayer() );
        guardian.spawn( event.getPlayer().getSpawnLocation().add( new Vector( 80, 20, 80 ) ) );
    }

}
