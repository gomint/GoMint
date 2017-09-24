package io.gomint.server.world.block;

/**
 * @author geNAZt
 */
public abstract class Liquid extends Block implements io.gomint.world.block.Liquid {

    public float getFillHeight() {
        short data = this.getBlockData();
        if ( data >= 8 ) {
            data = 0;
        }

        return ( data + 1 ) / 9f;
    }

}
