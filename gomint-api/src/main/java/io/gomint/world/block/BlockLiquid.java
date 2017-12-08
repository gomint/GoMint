package io.gomint.world.block;

/**
 * @author geNAZt
 */
public interface BlockLiquid {

    /**
     * Get the percentage of how high the fluid has been in this block
     *
     * @return a value between 0 and 1 where 1 is the whole block full of the given liquid
     */
    float getFillHeight();

}
