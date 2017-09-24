package io.gomint.server.world.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author geNAZt
 */
public class TemporaryStorage {

    private Map<String, Object> data;

    public <T, R> R store( String key, Function<T, R> func ) {
        // Check if we have a old value
        T old = null;
        if ( this.data != null ) {
            old = (T) this.data.get( key );
        }

        R result = func.apply( old );
        if ( result == null && old != null ) {
            this.data.remove( key );

            if ( this.data.size() == 0 ) {
                this.data = null;
            }
        } else if ( result != null ) {
            if ( this.data == null ) {
                this.data = new HashMap<>();
            }

            this.data.put( key, result );
        }

        return result;
    }

    public <T> T get( String key ) {
        if ( this.data == null ) {
            return null;
        }

        return (T) this.data.get( key );
    }

}
