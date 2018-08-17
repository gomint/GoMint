package io.gomint.server.world.block;

import lombok.Getter;

/**
 * @author Kaooot
 * @version 1.0
 */
public abstract class BlockAtom extends Block implements io.gomint.world.block.BlockAtom {

    @Getter
    private int protons;
    @Getter
    private int electrons;
    @Getter
    private int neutrons;
    @Getter
    private int atomicWeight;
}
