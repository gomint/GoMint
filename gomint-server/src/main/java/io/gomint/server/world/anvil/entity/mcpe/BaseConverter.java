package io.gomint.server.world.anvil.entity.mcpe;

import io.gomint.server.entity.Entity;
import io.gomint.server.entity.passive.EntityVillager;
import io.gomint.server.world.anvil.entity.EntityConverter;
import io.gomint.taglib.NBTTagCompound;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class BaseConverter<T> extends EntityConverter<T> {

    public void setRotation( NBTTagCompound compound, Entity entity ) {
        // Nukkit saves the position etc in doubles (for whatever reasons)
        List<Object> rotation = compound.getList( "Rotation", false );
        if ( rotation != null ) {
            float yaw = ensureFloat( rotation.get( 0 ) );
            float pitch = ensureFloat( rotation.get( 1 ) );

            entity.setYaw( yaw );
            entity.setPitch( pitch );
        }
    }

    public void setMotion( NBTTagCompound compound, Entity entity ) {
        // Nukkit saves the position etc in doubles (for whatever reasons)
        List<Object> motion = compound.getList( "Motion", false );
        if ( motion != null ) {
            float x = ensureFloat( motion.get( 0 ) );
            float y = ensureFloat( motion.get( 1 ) );
            float z = ensureFloat( motion.get( 2 ) );

            entity.getTransform().setMotion( x, y, z );
        }
    }

    public void setPosition( NBTTagCompound compound, Entity entity ) {
        // Nukkit saves the position etc in doubles (for whatever reasons)
        List<Object> pos = compound.getList( "Pos", false );
        if ( pos != null ) {
            float x = ensureFloat( pos.get( 0 ) );
            float y = ensureFloat( pos.get( 1 ) );
            float z = ensureFloat( pos.get( 2 ) );

            entity.setPosition( x, y, z );
        }
    }

    private float ensureFloat( Object o ) {
        if ( o instanceof Float ) {
            return (float) o;
        } else if ( o instanceof Double ) {
            return ( (Double) o ).floatValue();
        }

        return 0;
    }

}
