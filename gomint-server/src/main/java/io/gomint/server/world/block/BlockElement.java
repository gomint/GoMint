package io.gomint.server.world.block;

import lombok.Getter;

/**
 * @author Kaooot
 * @version 1.0
 */
public abstract class BlockElement extends Block {

    @Getter
    private int protons;
    @Getter
    private int electrons;
    @Getter
    private int neutrons;
    @Getter
    private int atomicWeight;
    @Getter
    private String name;
    @Getter
    private String symbol;
    @Getter
    private String metalKind;
    @Getter
    private float atomicMass;
}
