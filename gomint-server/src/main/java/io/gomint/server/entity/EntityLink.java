package io.gomint.server.entity;

import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class EntityLink {

    private long from;
    private long to;
    private byte unknown1;
    private byte unknown2;

}
