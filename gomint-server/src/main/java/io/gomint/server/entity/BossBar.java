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

    private final Entity entity;

    public BossBar(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void addPlayer( EntityPlayer player ) {
        io.gomint.server.entity.EntityPlayer p =  ( (io.gomint.server.entity.EntityPlayer) player );
        if ( p.getEntityVisibilityManager().isVisible( this.entity ) ) {
            PacketBossBar packet = new PacketBossBar();
            packet.setEntityId( this.entity.getEntityId() );
            packet.setType( PacketBossBar.Type.SHOW );
            p.getConnection().addToSendQueue( packet );
        }
    }

    @Override
    public void removePlayer( EntityPlayer player ) {
        io.gomint.server.entity.EntityPlayer p =  ( (io.gomint.server.entity.EntityPlayer) player );
        if ( p.getEntityVisibilityManager().isVisible( this.entity ) ) {
            PacketBossBar packet = new PacketBossBar();
            packet.setEntityId( this.entity.getEntityId() );
            packet.setType( PacketBossBar.Type.HIDE );
            p.getConnection().addToSendQueue( packet );
        }
    }

    @Override
    public void setTitle( String title ) {
        this.entity.setNameTag( title );
    }

    @Override
    public String getTitle() {
        return this.entity.getNameTag();
    }

}
