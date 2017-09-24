package io.gomint.server.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author geNAZt
 */
@AllArgsConstructor
@Getter
public enum PlayerPermissionMagicNumbers {

    VISITOR( 0 ),
    MEMBER( 1 ),
    OPERATOR( 2 ),
    CUSTOM( 3 );

    private int id;

}
