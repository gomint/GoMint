package io.gomint.server.player;

/**
 * @author geNAZt
 */
public enum PlayerPermission {

    VISITOR( 0 ),
    MEMBER( 1 ),
    OPERATOR( 2 ),
    CUSTOM( 3 );

    private int id;

    PlayerPermission(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
