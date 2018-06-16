package io.gomint.plugin.listener;

import io.gomint.entity.ChatType;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerMoveEvent;
import io.gomint.math.BlockPosition;
import io.gomint.plugin.TestPlugin;
import io.gomint.world.block.Block;
import lombok.RequiredArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class PlayerMoveListener implements EventListener {

    private final TestPlugin plugin;

    @EventHandler
    public void onPlayerMove( PlayerMoveEvent event ) {
        BlockPosition toBlock = event.getTo().toBlockPosition();
        if ( !toBlock.equals( event.getFrom().toBlockPosition() ) ) {
            Block block = event.getTo().getWorld().getBlockAt( toBlock );

            event.getPlayer().sendMessage( ChatType.POPUP,
                "§fX: §a" + toBlock.getX() + " §e- §fY: §a" + toBlock.getY() + " §e- §fZ: §a" + toBlock.getZ() + " | " + event.getPlayer().getPing() + " ms",
                "§fWalking on biome §a: " + block.getBiome() );
        }
    }

}
