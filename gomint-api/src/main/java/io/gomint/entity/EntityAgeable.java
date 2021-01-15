package io.gomint.entity;

/**
 * @author KCodeYT
 * @version 1.0
 * @stability 3
 */
public interface EntityAgeable<E> extends EntityLiving<E> {

    /**
     * Set this entity to a baby
     *
     * @param value true if this entity should be a baby, false if not
     */
    E baby(boolean value );

    /**
     * Is the entity a baby?
     *
     * @return true if this entity is a baby, false if not
     */
    boolean baby();

}
