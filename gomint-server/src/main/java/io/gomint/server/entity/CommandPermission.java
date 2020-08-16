package io.gomint.server.entity;

/**
 * @author geNAZt
 */
public enum CommandPermission {

    NORMAL( 0 ),
    OPERATOR( 1 ),
    HOST( 2 ),
    AUTOMATION( 3 ),
    ADMIN( 4 );

    private final int id;

    CommandPermission(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
