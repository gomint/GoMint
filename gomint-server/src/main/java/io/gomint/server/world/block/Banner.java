package io.gomint.server.world.block;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Banner extends Block {

    @Override
    public float getBlastResistance() {
        return 5f;
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

}
