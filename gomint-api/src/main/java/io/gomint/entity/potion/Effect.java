package io.gomint.entity.potion;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Effect {

    /**
     * Decide if the potion is visible (generates particles)
     *
     * @param value true when it should show particles, false when it shouldn't
     */
    void setVisible( boolean value );

}
