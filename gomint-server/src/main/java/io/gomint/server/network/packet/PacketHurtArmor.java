/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketHurtArmor extends Packet {

    private int damage;

    /**
     * Create new packet entity event
     */
    public PacketHurtArmor() {
        super( Protocol.PACKET_HURT_ARMOR );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.damage );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.damage = buffer.readSignedVarInt();
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
