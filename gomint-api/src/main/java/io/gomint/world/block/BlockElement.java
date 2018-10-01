package io.gomint.world.block;

/**
 * @author Kaooot
 * @version 1.0
 */
public interface BlockElement extends Block {

    int getProtons();

    int getElectrons();

    int getNeutrons();

    int getAtomicWeight();

    String getName();
}
