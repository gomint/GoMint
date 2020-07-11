package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketClientCacheBlobStatus;
import io.gomint.server.network.packet.PacketClientCacheMissResponse;
import io.gomint.server.network.packet.PacketClientCacheStatus;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketClientCacheBlobStatusHandler implements PacketHandler<PacketClientCacheBlobStatus> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketClientCacheBlobStatusHandler.class);

    @Override
    public void handle(PacketClientCacheBlobStatus packet, long currentTimeMillis, PlayerConnection connection) {
        double ratio = ( packet.getHit().length / (double)(packet.getHit().length + packet.getMiss().length) ) * 100;

        LOGGER.debug("Got {} hits, {} misses => {}% hit rate", packet.getHit().length, packet.getMiss().length, ratio);

        // Get the cache and fulfil the request if needed
        PacketClientCacheMissResponse response = new PacketClientCacheMissResponse();
        response.setData(new Long2ObjectOpenHashMap<>());

        for (long miss : packet.getMiss()) {
            ByteBuf buf = connection.getCache().get(miss);
            if ( buf != null ) {
                response.getData().put(miss, buf);
            }
        }

        for (long hit : packet.getHit()) {
            connection.getCache().remove(hit);
        }

        if (response.getData().size() > 0) {
            LOGGER.debug("Sending {} blobs to fill client cache", response.getData().size());
            connection.addToSendQueue(response);
        }

        connection.checkForSpawning();
    }

}
