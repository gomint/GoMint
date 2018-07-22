package io.gomint.plugin.listener;

import io.gomint.GoMint;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.entity.EntityDeathEvent;

import java.util.Collection;

public class EntityDeathListener implements EventListener {

    @EventHandler
    public void onEntityDeath( EntityDeathEvent event ) {
        Collection<EntityPlayer> players = GoMint.instance().getPlayers();

        players.forEach( all -> all.sendMessage( "One Entity with the ID [" + event.getEntity().getEntityId() + "] is died! " ) );
    }
}
