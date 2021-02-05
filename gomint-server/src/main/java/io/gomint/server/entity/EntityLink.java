package io.gomint.server.entity;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityLink {

    private long from;
    private long to;
    private byte unknown1;
    private byte unknown2;

    public long getFrom() {
        return this.from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return this.to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public byte getUnknown1() {
        return this.unknown1;
    }

    public void setUnknown1(byte unknown1) {
        this.unknown1 = unknown1;
    }

    public byte getUnknown2() {
        return this.unknown2;
    }

    public void setUnknown2(byte unknown2) {
        this.unknown2 = unknown2;
    }
}
