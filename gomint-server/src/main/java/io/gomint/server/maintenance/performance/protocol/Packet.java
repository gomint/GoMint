package io.gomint.server.maintenance.performance.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Packet {

    public abstract void serialize( DataOutputStream dataOutputStream ) throws IOException;

    public abstract void deserialize( DataInputStream dataInputStream ) throws IOException;

}
