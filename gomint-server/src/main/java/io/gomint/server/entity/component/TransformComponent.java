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
    public float motionX() {
        return this.motionX;
    }

    @Override
    public float motionY() {
        return this.motionY;
    }

    @Override
    public float motionZ() {
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
    public float positionX() {
        return this.posX;
    }

    @Override
    public float positionY() {
        return this.posY;
    }

    @Override
    public float positionZ() {
        return this.posZ;
    }

    @Override
    public Vector position() {
        return new Vector( this.posX, this.posY, this.posZ );
    }

    @Override
    public Transformable position(Vector position ) {
        this.posX = position.x();
        this.posY = position.y();
        this.posZ = position.z();
        this.dirty = true;
        return this;
    }

    @Override
    public float yaw() {
        return this.yaw;
    }

    @Override
    public Transformable yaw(float yaw ) {
        this.yaw = yaw;
        this.dirty = true;
        return this;
    }

    @Override
    public float headYaw() {
        return this.headYaw;
    }

    @Override
    public Transformable headYaw(float headYaw ) {
        this.headYaw = headYaw;
        this.dirty = true;
        return this;
    }

    @Override
    public float pitch() {
        return this.pitch;
    }

    @Override
    public Transformable pitch(float pitch ) {
        this.pitch = pitch;
        this.dirty = true;
        return this;
    }

    @Override
    public Vector direction() {
        double rY = Math.toRadians( this.yaw );
        double rP = Math.toRadians( this.pitch );

        float y = (float) -Math.sin( rP );
        double cosP = Math.cos( rP );

        float x = (float) ( -cosP * Math.sin( rY ) );
        float z = (float) ( cosP * Math.cos( rY ) );

        return new Vector( x, y, z );
    }

    @Override
    public Vector headDirection() {
        double rY = Math.toRadians( this.headYaw );
        double rP = Math.toRadians( this.pitch );
        double sinY = Math.sin( rY );
        double cosY = Math.cos( rY );
        double sinP = Math.sin( rP );
        double cosP = Math.cos( rP );

        return new Vector( (float) ( cosY * cosP ), (float) sinP, (float) ( sinY * cosP ) );
    }

    @Override
    public Transformable motion(float motionX, float motionY, float motionZ ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        return this;
    }

    @Override
    public Transformable manipulateMotion( float x, float y, float z ) {
        this.motionX += x;
        this.motionY += y;
        this.motionZ += z;
        return this;
    }

    @Override
    public Transformable position(float positionX, float positionY, float positionZ ) {
        this.posX = positionX;
        this.posY = positionY;
        this.posZ = positionZ;
        this.dirty = true;
        return this;
    }

    @Override
    public Transformable move( float offsetX, float offsetY, float offsetZ ) {
        this.posX += offsetX;
        this.posY += offsetY;
        this.posZ += offsetZ;
        this.dirty = true;
        return this;
    }

    @Override
    public Transformable move( Vector offset ) {
        return move( offset.x(), offset.y(), offset.z() );
    }

    @Override
    public Transformable rotateYaw( float yaw ) {
        // Add yaw rotation and normalize immediately:
        this.yaw += yaw;
        this.normalizeYaw();
        this.dirty = true;
        return this;
    }

    @Override
    public Transformable rotateHeadYaw( float headYaw ) {
        // Add head yaw rotation and normalize immediately:
        this.headYaw += headYaw;
        this.normalizeHeadYaw();
        this.dirty = true;
        return this;
    }

    @Override
    public Transformable rotatePitch( float pitch ) {
        // Add pitch rotation and normalize immediately:
        this.pitch += pitch;
        this.normalizePitch();
        this.dirty = true;
        return this;
    }

    @Override
    public Location toLocation( WorldAdapter world ) {
        return new Location( world, this.posX, this.posY, this.posZ, this.headYaw, this.yaw, this.pitch );
    }

    @Override
    public boolean dirty() {
        boolean result = this.dirty;
        this.dirty = false;
        return result;
    }

    @Override
    public Vector motion() {
        return new Vector( this.motionX, this.motionY, this.motionZ );
    }

    @Override
    public boolean motionBeenSent() {
        return this.sentMotion;
    }

    @Override
    public Transformable markMotionSent() {
        this.sentMotion = true;
        return this;
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
