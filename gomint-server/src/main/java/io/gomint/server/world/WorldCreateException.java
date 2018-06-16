package io.gomint.server.world;

/**
 * @author geNAZt
 * @version 1.0
 */
public class WorldCreateException extends Exception {

    /**
     * Create a new world create exception with a reason
     *
     * @param message which should be printed as the reason
     */
    public WorldCreateException( String message ) {
        super( message );
    }

    public WorldCreateException( String message, Exception e ) {
        super( message, e );
    }

}
