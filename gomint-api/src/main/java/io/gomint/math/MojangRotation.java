package io.gomint.math;

import io.gomint.entity.Entity;

/**
 * @author geNAZt
 * @version 1.0
 * <p>
 * Mojang rotations are stored in 16 different stages. A byte holds the correct value and can be multiplied with
 * 22.5f to get the correct amount of degrees
 */
public class MojangRotation {

    private byte rotationValue;

    /**
     * Construct a new mojang based rotation
     *
     * @param initValue of this rotation. must be degrees / 22.5f
     */
    public MojangRotation( byte initValue ) {
        this.rotationValue = initValue;
    }

    /**
     * Get the correct value for metadata
     *
     * @return rotation divided by 22.5f (to be stored in blocks)
     */
    public byte getRotationValue() {
        return this.rotationValue;
    }

    /**
     * Create a new rotation from a entity
     *
     * @param entity which should be used for gettin the rotation
     * @return mojang rotation for block metadata
     */
    public static MojangRotation fromEntityForBlock( Entity entity ) {
        float yaw = entity.getLocation().getHeadYaw();
        return new MojangRotation( (byte) ( yaw / 22.5f ) );
    }

}
