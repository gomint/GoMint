package io.gomint.testplugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.EventPriority;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.inventory.item.ItemDiamondSword;
import io.gomint.testplugin.TestPlugin;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerJoinListener implements EventListener {

    private final TestPlugin plugin;

    public PlayerJoinListener(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Set to allow all permissions
        event.player().permissionManager().permission("*", true);

        // Give this player the debug scoreboard
        // new DebugScoreboard(this.plugin, event.getPlayer());

        ItemDiamondSword sword = ItemDiamondSword.create(1);
        event.player().inventory().item(6, sword);

        event.player().level(27);
    }

}
