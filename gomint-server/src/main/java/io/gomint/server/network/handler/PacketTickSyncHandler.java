package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketTickSync;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketTickSyncHandler implements PacketHandler<PacketTickSync> {

    @Override
    public void handle(PacketTickSync packet, long currentTimeMillis, PlayerConnection connection) {
        PacketTickSync sync = new PacketTickSync();
        sync.setServerTiming(packet.getServerTiming());
        connection.addToSendQueue(sync);
    }

}
