package io.gomint.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author geNAZt
 */
@Getter
@AllArgsConstructor
public enum CommandPermission {

    NORMAL( 0 ),
    OPERATOR( 1 ),
    HOST( 2 ),
    AUTOMATION( 3 ),
    ADMIN( 4 );

    private int id;

}
