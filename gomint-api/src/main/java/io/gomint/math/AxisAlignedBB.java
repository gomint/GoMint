package io.gomint.math;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class AxisAlignedBB implements Cloneable {

    private float minX;
    private float minY;
    private float minZ;
    private float maxX;
    private float maxY;
    private float maxZ;

    /**
     * Construct a new BoundingBox with the min and max coordinates given
     *
     * @param minX Minimum X Coordinate
     * @param minY Minimum Y Coordinate
     * @param minZ Minimum Z Coordinate
     * @param maxX Maximum X Coordinate
     * @param maxY Maximum Y Coordinate
     * @param maxZ Maximum Z Coordinate
     */
    public AxisAlignedBB( float minX, float minY, float minZ, float maxX, float maxY, float maxZ ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    /**
     * Set new bounds
     *
     * @param minX Minimum X Coordinate
     * @param minY Minimum Y Coordinate
     * @param minZ Minimum Z Coordinate
     * @param maxX Maximum X Coordinate
     * @param maxY Maximum Y Coordinate
     * @param maxZ Maximum Z Coordinate
     * @return the Bounding Box with new bounds
     */
    public AxisAlignedBB bounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        return this;
    }

    /**
     * Set new bounds
     *
     * @param other the other Bounding Box from which we should copy
     * @return the Bounding Box with new bounds
     */
    public AxisAlignedBB bounds(AxisAlignedBB other ) {
        this.minX = other.minX;
        this.minY = other.minY;
        this.minZ = other.minZ;
        this.maxX = other.maxX;
        this.maxY = other.maxY;
        this.maxZ = other.maxZ;
        return this;
    }

    /**
     * Add coordinates to the Bounding Box
     *
     * @param x the X coordinate which should be added
     * @param y the Y coordinate which should be added
     * @param z the Z coordinate which should be added
     * @return a new Bounding Box which contains the addition of the coordinates
     */
    public AxisAlignedBB addCoordinates( float x, float y, float z ) {
        float minX = this.minX;
        float minY = this.minY;
        float minZ = this.minZ;
        float maxX = this.maxX;
        float maxY = this.maxY;
        float maxZ = this.maxZ;

        // Manipulate x axis
        if ( x < 0 ) {
            minX += x;
        } else if ( x > 0 ) {
            maxX += x;
        }

        // Manipulate y axis
        if ( y < 0 ) {
            minY += y;
        } else if ( y > 0 ) {
            maxY += y;
        }

        // Manipulate z axis
        if ( z < 0 ) {
            minZ += z;
        } else if ( z > 0 ) {
            maxZ += z;
        }

        return new AxisAlignedBB( minX, minY, minZ, maxX, maxY, maxZ );
    }

    /**
     * Grow the Bounding Box and return a new one
     *
     * @param x the X coordinate to grow in both directions
     * @param y the Y coordinate to grow in both directions
     * @param z the Z coordinate to grow in both directions
     * @return a new Bounding Box which has been grown by the amount given
     */
    public AxisAlignedBB grow( float x, float y, float z ) {
        return new AxisAlignedBB( this.minX - x, this.minY - y, this.minZ - z, this.maxX + x, this.maxY + y, this.maxZ + z );
    }

    /**
     * Expand this Bounding Box by the given coordinates
     *
     * @param x the X coordinate to expand in both directions
     * @param y the Y coordinate to expand in both directions
     * @param z the Z coordinate to expand in both directions
     * @return this modified Bounding Box
     */
    public AxisAlignedBB expand( float x, float y, float z ) {
        this.minX -= x;
        this.minY -= y;
        this.minZ -= z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    /**
     * Offset the Bounding Box by the given coordinates
     *
     * @param x the X coordinate for how much we should offset
     * @param y the Y coordinate for how much we should offset
     * @param z the Z coordinate for how much we should offset
     * @return this modified Bounding Box
     */
    public AxisAlignedBB offset( float x, float y, float z ) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
        return this;
    }

    /**
     * Shrink the Bounding Box and return a new one
     *
     * @param x the X coordinate to shrink in both directions
     * @param y the Y coordinate to shrink in both directions
     * @param z the Z coordinate to shrink in both directions
     * @return a new Bounding Box which has been grown by the amount given
     */
    public AxisAlignedBB shrink( float x, float y, float z ) {
        return new AxisAlignedBB( this.minX + x, this.minY + y, this.minZ + z, this.maxX - x, this.maxY - y, this.maxZ - z );
    }

    /**
     * Contract this Bounding Box by the given coordinates
     *
     * @param x the X coordinate to contract in both directions
     * @param y the Y coordinate to contract in both directions
     * @param z the Z coordinate to contract in both directions
     * @return this modified Bounding Box
     */
    public AxisAlignedBB contract( float x, float y, float z ) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX -= x;
        this.maxY -= y;
        this.maxZ -= z;
        return this;
    }

    /**
     * Offset the Bounding Box by the given coordinates and return a new one
     *
     * @param x the X coordinate for how much we should offset
     * @param y the Y coordinate for how much we should offset
     * @param z the Z coordinate for how much we should offset
     * @return a new Bounding Box which has been offset
     */
    public AxisAlignedBB offsetBoundingBox(float x, float y, float z ) {
        return new AxisAlignedBB( this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z );
    }

    /**
     * Get the offset in x axis
     *
     * @param bb the bounding box from which we want to know the offset to
     * @param x  default or maximum offset allowed
     * @return offset or capped value
     */
    public float calculateXOffset( AxisAlignedBB bb, float x ) {
        // Check if we are outside of Y bounds
        if ( bb.maxY <= this.minY || bb.minY >= this.maxY ) {
            return x;
        }

        // Check if we are outside of Z bounds
        if ( bb.maxZ <= this.minZ || bb.minZ >= this.maxZ ) {
            return x;
        }

        // Check if we have a positive default offset
        if ( x > 0 && bb.maxX <= this.minX ) {
            // Get the real offset and cap it at the default offset
            float x1 = this.minX - bb.maxX;
            if ( x1 < x ) {
                x = x1;
            }
        }

        // Check if we have a negative default offset
        if ( x < 0 && bb.minX >= this.maxX ) {
            // Get the real offset and cap it at the default offset
            float x2 = this.maxX - bb.minX;
            if ( x2 > x ) {
                x = x2;
            }
        }

        return x;
    }

    /**
     * Get the offset in y axis
     *
     * @param bb the bounding box from which we want to know the offset to
     * @param y  default or maximum offset allowed
     * @return offset or capped value
     */
    public float calculateYOffset( AxisAlignedBB bb, float y ) {
        // Check if we are outside of X bounds
        if ( bb.maxX <= this.minX || bb.minX >= this.maxX ) {
            return y;
        }

        // Check if we are outside of Z bounds
        if ( bb.maxZ <= this.minZ || bb.minZ >= this.maxZ ) {
            return y;
        }

        // Check if we have a positive default offset
        if ( y > 0 && bb.maxY <= this.minY ) {
            // Get the real offset and cap it at the default offset
            float y1 = this.minY - bb.maxY;
            if ( y1 < y ) {
                y = y1;
            }
        }

        // Check if we have a negative default offset
        if ( y < 0 && bb.minY >= this.maxY ) {
            // Get the real offset and cap it at the default offset
            float y2 = this.maxY - bb.minY;
            if ( y2 > y ) {
                y = y2;
            }
        }

        return y;
    }

    /**
     * Get the offset in z axis
     *
     * @param bb the bounding box from which we want to know the offset to
     * @param z  default or maximum offset allowed
     * @return offset or capped value
     */
    public float calculateZOffset( AxisAlignedBB bb, float z ) {
        // Check if we are outside of X bounds
        if ( bb.maxX <= this.minX || bb.minX >= this.maxX ) {
            return z;
        }

        // Check if we are outside of Y bounds
        if ( bb.maxY <= this.minY || bb.minY >= this.maxY ) {
            return z;
        }

        // Check if we have a positive default offset
        if ( z > 0 && bb.maxZ <= this.minZ ) {
            // Get the real offset and cap it at the default offset
            float z1 = this.minZ - bb.maxZ;
            if ( z1 < z ) {
                z = z1;
            }
        }

        // Check if we have a negative default offset
        if ( z < 0 && bb.minZ >= this.maxZ ) {
            // Get the real offset and cap it at the default offset
            float z2 = this.maxZ - bb.minZ;
            if ( z2 > z ) {
                z = z2;
            }
        }

        return z;
    }

    /**
     * Check if we intersect with the given Bounding Box
     *
     * @param bb the other bounding box we want to check for intersection with
     * @return true when the given Bounding Box intersects with this one, false when not
     */
    public boolean intersectsWith( AxisAlignedBB bb ) {
        if ( bb.maxX - this.minX > MathUtils.EPSILON && this.maxX - bb.minX > MathUtils.EPSILON ) {
            if ( bb.maxY - this.minY > MathUtils.EPSILON && this.maxY - bb.minY > MathUtils.EPSILON ) {
                return bb.maxZ - this.minZ > MathUtils.EPSILON && this.maxZ - bb.minZ > MathUtils.EPSILON;
            }
        }

        return false;
    }

    /**
     * Check if the given Vector lies within this Bounding Box
     *
     * @param vector the vector which may or may not be in this Bounding Box
     * @return true when the vector is inside this Bounding Box, false when not
     */
    public boolean isVectorInside( Vector vector ) {
        return !( vector.x <= this.minX || vector.x >= this.maxX ) &&
            !( vector.y <= this.minY || vector.y >= this.maxY ) &&
            ( vector.z > this.minZ || vector.z < this.maxZ );
    }

    /**
     * Get the average edge length of this Bounding Box
     *
     * @return the average edge length
     */
    public float averageEdgeLength() {
        return ( this.maxX - this.minX + this.maxY - this.minY + this.maxZ - this.minZ ) / 3;
    }

    public boolean isVectorInYZ( Vector vector ) {
        return vector.y >= this.minY && vector.y <= this.maxY && vector.z >= this.minZ && vector.z <= this.maxZ;
    }

    public boolean isVectorInXZ( Vector vector ) {
        return vector.x >= this.minX && vector.x <= this.maxX && vector.z >= this.minZ && vector.z <= this.maxZ;
    }

    public boolean isVectorInXY( Vector vector ) {
        return vector.x >= this.minX && vector.x <= this.maxX && vector.y >= this.minY && vector.y <= this.maxY;
    }

    /**
     * Calculate the vector which is in line with this bounding box.
     * <p>
     * |---------x----------|
     * pos1    this bb     pos2
     *
     * @param pos1 from the start
     * @param pos2 from the end
     * @return null when not on line or vector we found
     */
    public Vector calculateIntercept( Vector pos1, Vector pos2 ) {
        Vector v1 = pos1.vectorWhenXIsOnLine( pos2, this.minX );
        Vector v2 = pos1.vectorWhenXIsOnLine( pos2, this.maxX );
        Vector v3 = pos1.vectorWhenYIsOnLine( pos2, this.minY );
        Vector v4 = pos1.vectorWhenYIsOnLine( pos2, this.maxY );
        Vector v5 = pos1.vectorWhenZIsOnLine( pos2, this.minZ );
        Vector v6 = pos1.vectorWhenZIsOnLine( pos2, this.maxZ );

        Vector resultVector = null;
        if ( v1 != null && this.isVectorInYZ( v1 ) ) {
            resultVector = v1;
        }

        if ( v2 != null && this.isVectorInYZ( v2 ) &&
            ( resultVector == null || pos1.distanceSquared( v2 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v2;
        }

        if ( v3 != null && this.isVectorInXZ( v3 ) &&
            ( resultVector == null || pos1.distanceSquared( v3 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v3;
        }

        if ( v4 != null && this.isVectorInXZ( v4 ) &&
            ( resultVector == null || pos1.distanceSquared( v4 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v4;
        }

        if ( v5 != null && this.isVectorInXY( v5 ) &&
            ( resultVector == null || pos1.distanceSquared( v5 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v5;
        }

        if ( v6 != null && this.isVectorInXY( v6 ) &&
            ( resultVector == null || pos1.distanceSquared( v6 ) < pos1.distanceSquared( resultVector ) ) ) {
            resultVector = v6;
        }

        return resultVector;
    }

    @Override
    public AxisAlignedBB clone() {
        try {
            AxisAlignedBB clone = (AxisAlignedBB) super.clone();
            return clone.bounds( this );
        } catch ( CloneNotSupportedException e ) {
            return new AxisAlignedBB( this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ );
        }
    }

    public float minX() {
        return this.minX;
    }

    public float minY() {
        return this.minY;
    }

    public float minZ() {
        return this.minZ;
    }

    public float maxX() {
        return this.maxX;
    }

    public float maxY() {
        return this.maxY;
    }

    public float maxZ() {
        return this.maxZ;
    }

    @Override
    public String toString() {
        return "AxisAlignedBB{" +
            "minX=" + this.minX +
            ", minY=" + this.minY +
            ", minZ=" + this.minZ +
            ", maxX=" + this.maxX +
            ", maxY=" + this.maxY +
            ", maxZ=" + this.maxZ +
            '}';
    }

}
