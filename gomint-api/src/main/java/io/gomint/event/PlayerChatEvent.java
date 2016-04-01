package io.gomint.event;

import io.gomint.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Called whenever a player sends a chat message.
 *
 * @author Digot
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class PlayerChatEvent extends CancelableEvent {

    private Player player;
    private String message;

}
