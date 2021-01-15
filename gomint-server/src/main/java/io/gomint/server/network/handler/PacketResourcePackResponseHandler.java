package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerPreJoinEvent;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.PlayerConnectionState;
import io.gomint.server.network.packet.PacketResourcePackResponse;
import io.gomint.server.network.packet.PacketResourcePackStack;
import io.gomint.server.resource.PackIdVersion;
import io.gomint.server.resource.ResourcePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketResourcePackResponseHandler implements PacketHandler<PacketResourcePackResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketResourcePackResponseHandler.class);

    @Override
    public void handle(PacketResourcePackResponse packet, long currentTimeMillis, PlayerConnection connection) {
        // TODO: Implement resource pack sending
        switch (packet.getStatus()) {
            case HAVE_ALL_PACKS:
                LOGGER.info("Login state: HAVE_ALL_PACKS reached: {}", connection.getEntity());

                PacketResourcePackStack packetResourcePackStack = new PacketResourcePackStack();

                packetResourcePackStack.setResourcePackEntries(new ArrayList<>() {{
                    add(new ResourcePack(
                        new PackIdVersion(UUID.fromString("0fba4063-dba1-4281-9b89-ff9390653530"), "1.0.0"),
                        0
                    ));
                }});

                connection.send(packetResourcePackStack);
                break;

            case COMPLETED:
                LOGGER.info("Login state: COMPLETED reached: {}", connection.getEntity());

                // Proceed with login
                this.switchToLogin(connection, currentTimeMillis);

                break;
        }
    }

    private void switchToLogin(PlayerConnection connection, long currentTimeMillis) {
        // Proceed with login
        connection.setState(PlayerConnectionState.LOGIN);
        LOGGER.info("Logging in as " + connection.getEntity().name() + " with id " + connection.getEntity().id());

        connection.getEntity().loginPerformance().setResourceEnd(currentTimeMillis);

        PlayerPreJoinEvent playerPreJoinEvent = new PlayerPreJoinEvent(connection.getEntity());
        connection.getServer().pluginManager().callEvent(playerPreJoinEvent);
        if (!playerPreJoinEvent.cancelled()) {
            connection.getEntity().prepareEntity();
        }
    }

}
