package io.gomint.plugin.listener;

import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.inventory.item.*;
import io.gomint.inventory.item.data.DyeType;
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
        // event.getPlayer().teleport( GoMint.instance().getDefaultWorld().getSpawnLocation().clone().add( 0, 1, 0 ) );

        event.getPlayer().getInventory().setItem( 0, ItemEnchantmentTable.create( 1 ) );

        ItemDye lapis = ItemDye.create( 64 );
        lapis.setDyeType( DyeType.LAPIS_LAZULI );
        event.getPlayer().getInventory().setItem( 1, lapis );
        event.getPlayer().setLevel( 60 );

        event.getPlayer().getInventory().setItem( 2, ItemDiamondSword.create( 1 ) );
    }

}
