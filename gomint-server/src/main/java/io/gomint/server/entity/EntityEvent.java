package io.gomint.server.entity;

import lombok.Getter;

/**
 * @author geNAZt
 */
public enum EntityEvent {

    HURT(2),
    DEATH(3),

    RESPAWN(18);

    @Getter private final byte id;
    EntityEvent( int id ) {
        this.id = (byte) id;
    }

}
