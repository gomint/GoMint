package io.gomint.plugin.listener;

import io.gomint.GoMint;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.inventory.item.*;
import io.gomint.plugin.TestPlugin;
import lombok.RequiredArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class PlayerJoinListener implements EventListener {

    private final TestPlugin plugin;

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        event.getPlayer().setDisplayName( "§6Project Lead §7|§6 " + event.getPlayer().getName() );
        event.getPlayer().teleport( GoMint.instance().getDefaultWorld().getSpawnLocation().clone().add( 0, 1, 0 ) );

        event.getPlayer().getInventory().setItem( 0, ItemFishingRod.create( 1 ) );
        event.getPlayer().getInventory().setItem( 1, ItemSandstone.create( 64 ) );

        event.getPlayer().getInventory().setItem( 2, ItemIronPickaxe.create( 1 ) );

        ItemBucket bucket = ItemBucket.create( 1 );
        bucket.setContent( ItemBucket.Content.LAVA );
        event.getPlayer().getInventory().setItem( 3, bucket );

        event.getPlayer().getInventory().setItem( 4, ItemEnderPearl.create( 1 ) );
    }

}
