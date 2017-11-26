package io.gomint.plugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.gui.CustomForm;
import io.gomint.gui.FormListener;
import io.gomint.gui.FormResponse;
import io.gomint.gui.Modal;
import io.gomint.inventory.item.*;
import io.gomint.plugin.TestPlugin;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class PlayerJoinListener implements EventListener {

    private final TestPlugin plugin;

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        event.getPlayer().getInventory().setItem( 0, ItemCake.create( 1 ) );

        event.getPlayer().getInventory().setItem( 1, ItemCarrot.create( 64 ) );
        event.getPlayer().getInventory().setItem( 2, ItemGoldenCarrot.create( 64 ) );
    }

}
