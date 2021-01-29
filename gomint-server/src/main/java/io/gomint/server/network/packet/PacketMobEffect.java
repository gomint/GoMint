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
public class PacketMobEffect extends Packet {

    // CHECKSTYLE:OFF
    public static final byte EVENT_ADD = 1;
    public static final byte EVENT_MODIFY = 2;
    public static final byte EVENT_REMOVE = 3;
    // CHECKSTYLE:ON

    private long entityId;
    private byte action;
    private int effectId;
    private int amplifier;
    private boolean visible;
    private int duration;

    /**
     * Construct a new packet
     */
    public PacketMobEffect() {
        super( Protocol.PACKET_MOB_EFFECT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );
        buffer.writeByte( this.action );
        buffer.writeSignedVarInt( this.effectId );
        buffer.writeSignedVarInt( this.amplifier );
        buffer.writeBoolean( this.visible );
        buffer.writeSignedVarInt( this.duration );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public byte getAction() {
        return this.action;
    }

    public void setAction(byte action) {
        this.action = action;
    }

    public int getEffectId() {
        return this.effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
