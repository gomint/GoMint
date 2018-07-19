package io.gomint.server.entity.generator;

import io.gomint.server.entity.Entity;
import io.gomint.server.entity.monster.EntityDrowned;

public class EntityDrownedGenerator implements EntityGenerator<Entity> {

    @Override
    public io.gomint.entity.Entity generate() {
        return new EntityDrowned();
    }
}
