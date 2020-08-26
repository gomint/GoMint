package io.gomint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 *
 * This is the only class in the API which is not part of the public API. We need it to get the server implementation
 * linked for the API.
 */
public class GoMintInstanceHolder {

    /**
     * Holder for the started instance
     */
    static GoMint instance;

    /**
     * Set the GoMint instance into this holder
     *
     * @param instance which has been started
     */
    public static void setInstance( GoMint instance ) {
        GoMintInstanceHolder.instance = instance;
    }

}
