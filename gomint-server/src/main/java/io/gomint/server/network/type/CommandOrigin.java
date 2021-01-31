package io.gomint.server.network.type;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandOrigin {

    private byte unknown1;
    private UUID uuid;
    private byte unknown2;
    private byte type; // 0x00 player, 0x03 server

    public CommandOrigin(byte unknown1, UUID uuid, byte unknown2, byte type) {
        this.unknown1 = unknown1;
        this.uuid = uuid;
        this.unknown2 = unknown2;
        this.type = type;
    }

    public byte unknown1() {
        return this.unknown1;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public byte unknown2() {
        return this.unknown2;
    }

    public byte type() {
        return this.type;
    }

    public CommandOrigin unknown1(byte unknown1) {
        this.unknown1 = unknown1;
        return this;
    }

    public CommandOrigin uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public CommandOrigin unknown2(byte unknown2) {
        this.unknown2 = unknown2;
        return this;
    }

    public CommandOrigin type(byte type) {
        this.type = type;
        return this;
    }
}
