package io.gomint.server.entity.component;

import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Transformable;
import io.gomint.server.world.WorldAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A component that provides a solid implementation of Transformable and may be added to entities.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class TransformComponent implements EntityComponent, Transformable {

    private static final Logger LOGGER = LoggerFactory.getLogger( TransformComponent.class );

    private float posX;
    private float posY;
    private float posZ;

    private float motionX;
    private float motionY;
    private float motionZ;

    private float yaw;
    private float headYaw;
    private float pitch;

    private boolean dirty;
    private boolean sentMotion;

    /**
     * Construct a basic transformer component
     */
    public TransformComponent() {

    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        // Nothing
    }

    @Override
    public float getMotionX() {
        return this.motionX;
    }

    @Override
    public float getMotionY() {
        return this.motionY;
    }

    @Override
    public float getMotionZ() {
        return this.motionZ;
    }

    public void setMotionX(float motionX) {
        this.motionX = motionX;
    }

    public void setMotionY(float motionY) {
        this.motionY = motionY;
    }

    public void setMotionZ(float motionZ) {
        this.motionZ = motionZ;
    }

    @Override
    public float getPositionX() {
        return this.posX;
    }

    @Override
    public float getPositionY() {
        return this.posY;
    }

    @Override
    public float getPositionZ() {
        return this.posZ;
    }

    @Override
    public Vector getPosition() {
        return new Vector( this.posX, this.posY, this.posZ );
    }

    @Override
    public void setPosition( Vector position ) {
        this.posX = position.getX();
        this.posY = position.getY();
        this.posZ = position.getZ();
        this.dirty = true;
    }

    @Override
    public float getYaw() {
        return this.yaw;
    }

    @Override
    public void setYaw( float yaw ) {
        this.yaw = yaw;
        this.dirty = true;
    }

    @Override
    public float getHeadYaw() {
        return this.headYaw;
    }

    @Override
    public void setHeadYaw( float headYaw ) {
        this.headYaw = headYaw;
        this.dirty = true;
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    @Override
    public void setPitch( float pitch ) {
        this.pitch = pitch;
        this.dirty = true;
    }

    @Override
    public Vector getDirection() {
        double rY = Math.toRadians( this.yaw );
        double rP = Math.toRadians( this.pitch );

        float y = (float) -Math.sin( rP );
        double cosP = Math.cos( rP );

        float x = (float) ( -cosP * Math.sin( rY ) );
        float z = (float) ( cosP * Math.cos( rY ) );

        return new Vector( x, y, z );
    }

    @Override
    public Vector getHeadDirection() {
        double rY = Math.toRadians( this.headYaw );
        double rP = Math.toRadians( this.pitch );
        double sinY = Math.sin( rY );
        double cosY = Math.cos( rY );
        double sinP = Math.sin( rP );
        double cosP = Math.cos( rP );

        return new Vector( (float) ( cosY * cosP ), (float) sinP, (float) ( sinY * cosP ) );
    }

    @Override
    public void setMotion( float motionX, float motionY, float motionZ ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    @Override
    public void manipulateMotion( float x, float y, float z ) {
        this.motionX += x;
        this.motionY += y;
        this.motionZ += z;
    }

    @Override
    public void setPosition( float positionX, float positionY, float positionZ ) {
        this.posX = positionX;
        this.posY = positionY;
        this.posZ = positionZ;
        this.dirty = true;
    }

    @Override
    public void move( float offsetX, float offsetY, float offsetZ ) {
        this.posX += offsetX;
        this.posY += offsetY;
        this.posZ += offsetZ;
        this.dirty = true;
    }

    @Override
    public void move( Vector offset ) {
        move( offset.getX(), offset.getY(), offset.getZ() );
    }

    @Override
    public void rotateYaw( float yaw ) {
        // Add yaw rotation and normalize immediately:
        this.yaw += yaw;
        this.normalizeYaw();
        this.dirty = true;
    }

    @Override
    public void rotateHeadYaw( float headYaw ) {
        // Add head yaw rotation and normalize immediately:
        this.headYaw += headYaw;
        this.normalizeHeadYaw();
        this.dirty = true;
    }

    @Override
    public void rotatePitch( float pitch ) {
        // Add pitch rotation and normalize immediately:
        this.pitch += pitch;
        this.normalizePitch();
        this.dirty = true;
    }

    @Override
    public Location toLocation( WorldAdapter world ) {
        return new Location( world, this.posX, this.posY, this.posZ, this.headYaw, this.yaw, this.pitch );
    }

    @Override
    public boolean isDirty() {
        boolean result = this.dirty;
        this.dirty = false;
        return result;
    }

    @Override
    public Vector getMotion() {
        return new Vector( this.motionX, this.motionY, this.motionZ );
    }

    @Override
    public boolean hasMotionBeenSent() {
        return this.sentMotion;
    }

    @Override
    public void markMotionSent() {
        this.sentMotion = true;
    }

    /**
     * Normalizes the yaw angle of the object's body.
     */
    private void normalizeYaw() {
        while ( this.yaw < -180.0F ) {
            this.yaw += 360.0F;
        }
        while ( this.yaw > +180.0F ) {
            this.yaw -= 360.0F;
        }
    }

    /**
     * Normalizes the yaw angle of the object's head.
     */
    private void normalizeHeadYaw() {
        while ( this.headYaw < -180.0F ) {
            this.headYaw += 360.0F;
        }
        while ( this.headYaw > +180.0F ) {
            this.headYaw -= 360.0F;
        }
    }

    /**
     * Normalizes the pitch angle of the object's head.
     */
    private void normalizePitch() {
        if ( this.pitch >= 90.0F ) {
            this.pitch = +89.9F;
        }
        if ( this.pitch <= -90.0F ) {
            this.pitch = -89.9F;
        }
    }

}
