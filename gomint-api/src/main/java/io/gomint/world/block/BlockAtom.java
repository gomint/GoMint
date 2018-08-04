package io.gomint.world.block;

/**
 * @author Kaooot
 * @version 1.0
 */
public interface BlockAtom extends Block {

    /**
     * Get the protons of the atom
     *
     * @return protons
     */
    int getProtons();

    /**
     * Get the electrons of the atom
     *
     * @return electrons
     */
    int getElectrons();

    /**
     * Get the neutrons of the atom
     *
     * @return neutrons
     */
    int getNeutrons();

    /**
     * Get the atomic weight of the atom
     *
     * @return atomic weight
     */

    int getAtomicWeight();
}
