package io.gomint.server.entity;

/**
 * @author geNAZt
 */
public enum EntityEvent {

    HURT(2),
    DEATH(3),

    RESPAWN(18);

    private final byte id;

    EntityEvent( int id ) {
        this.id = (byte) id;
    }

    public byte getId() {
        return id;
    }

}
