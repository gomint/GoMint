/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.entity.EntityPlayer;
import io.gomint.server.network.packet.PacketBossBar;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BossBar implements io.gomint.entity.BossBar {

    private final Entity<?> entity;

    public BossBar(Entity<?> entity) {
        this.entity = entity;
    }

    @Override
    public BossBar addPlayer(EntityPlayer player ) {
        io.gomint.server.entity.EntityPlayer p =  ( (io.gomint.server.entity.EntityPlayer) player );
        if ( p.entityVisibilityManager().isVisible( this.entity ) ) {
            PacketBossBar packet = new PacketBossBar();
            packet.setEntityId( this.entity.id() );
            packet.setType( PacketBossBar.Type.SHOW );
            p.connection().addToSendQueue( packet );
        }

        return this;
    }

    @Override
    public BossBar removePlayer( EntityPlayer player ) {
        io.gomint.server.entity.EntityPlayer p =  ( (io.gomint.server.entity.EntityPlayer) player );
        if ( p.entityVisibilityManager().isVisible( this.entity ) ) {
            PacketBossBar packet = new PacketBossBar();
            packet.setEntityId( this.entity.id() );
            packet.setType( PacketBossBar.Type.HIDE );
            p.connection().addToSendQueue( packet );
        }

        return this;
    }

    @Override
    public BossBar title(String title ) {
        this.entity.nameTag( title );
        return this;
    }

    @Override
    public String title() {
        return this.entity.nameTag();
    }

}
