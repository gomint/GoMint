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
public class PacketSetObjective extends Packet {

    private String displaySlot;
    private String objectiveName;
    private String displayName;
    private String criteriaName;
    private int sortOrder;

    /**
     * Create new packet
     */
    public PacketSetObjective() {
        super( Protocol.PACKET_SET_OBJECTIVE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeString( this.displaySlot );
        buffer.writeString( this.objectiveName );
        buffer.writeString( this.displayName );
        buffer.writeString( this.criteriaName );
        buffer.writeSignedVarInt( this.sortOrder );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public String getDisplaySlot() {
        return this.displaySlot;
    }

    public void setDisplaySlot(String displaySlot) {
        this.displaySlot = displaySlot;
    }

    public String getObjectiveName() {
        return this.objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCriteriaName() {
        return this.criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
