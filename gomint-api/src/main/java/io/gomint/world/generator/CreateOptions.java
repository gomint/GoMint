package io.gomint.world.generator;

import io.gomint.world.WorldType;
import io.gomint.world.generator.integrated.LayeredGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author geNAZt
 * @version 1.0
 */
@Accessors( fluent = true )
@Setter
@Getter
public class CreateOptions {

    private WorldType worldType = WorldType.ANVIL;
    private Class<? extends ChunkGenerator> generator = LayeredGenerator.class;

}
