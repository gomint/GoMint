package io.gomint.event.world;

import io.gomint.event.Event;
import io.gomint.event.interfaces.WorldEvent;
import io.gomint.world.World;

public class SimpleWorldEvent extends Event implements WorldEvent {
    
    private final World world;

    public SimpleWorldEvent(World world) {
        this.world = world;
    }

    @Override
    public World world() {
        return this.world;
    }
}
