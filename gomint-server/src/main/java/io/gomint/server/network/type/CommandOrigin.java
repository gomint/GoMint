package io.gomint.server.network.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@ToString
@Accessors( chain = true )
public class CommandOrigin {

    private byte unknown1;
    private UUID uuid;
    private byte unknown2;
    @Setter private byte type; // 0x00 player, 0x03 server

}
