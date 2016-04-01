package io.gomint.event;

import io.gomint.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Digot
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class PlayerJoinEvent extends Event {

    private Player player;
    @Setter private String joinMessage;

}
