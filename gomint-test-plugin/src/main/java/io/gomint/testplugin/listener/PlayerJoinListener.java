package io.gomint.testplugin.listener;

import io.gomint.entity.passive.EntityArmorStand;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.EventPriority;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.inventory.item.ItemBucket;
import io.gomint.math.BlockPosition;
import io.gomint.testplugin.TestPlugin;
import io.gomint.testplugin.scoreboard.DebugScoreboard;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockBambooSapling;
import lombok.RequiredArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class PlayerJoinListener implements EventListener {

    private final TestPlugin plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Set to allow all permissions
        event.getPlayer().getPermissionManager().setPermission("*", true);

        // event.getPlayer().setGamemode( Gamemode.CREATIVE );

        // Give this player the debug scoreboard
        new DebugScoreboard(this.plugin, event.getPlayer());

        ItemBucket bucket = ItemBucket.create(1);
        bucket.setContent(ItemBucket.Content.WATER);
        event.getPlayer().getInventory().setItem(4, bucket);

        // Spawn a Armor Stand to the player location
        EntityArmorStand.create().spawn(event.getPlayer().getLocation());

        // Try to make a invalid block placement
        BlockPosition pos = event.getPlayer().getLocation().toBlockPosition();
        pos.add(0, -1, 0);
        Block air = event.getPlayer().getWorld().getBlockAt(pos);
        air.setType(BlockBambooSapling.class);
    }

}
