package io.gomint.server.network.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class CommandOrigin {

    private byte unknown1;
    private byte unknown2;
    private byte type; // 0x00 player, 0x03 server

}
