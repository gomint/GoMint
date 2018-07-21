package io.gomint.server.entity.generator;

import io.gomint.server.entity.Entity;

public class EntityVindicatorGenerator implements EntityGenerator<Entity> {

    public io.gomint.entity.Entity generate() {
        return new io.gomint.server.entity.monster.EntityVindicator();
    }
}
