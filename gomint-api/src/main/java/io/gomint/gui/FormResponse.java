package io.gomint.gui;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface FormResponse {

    /**
     * Get the value of the toggle given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    Boolean toggle(String id );

    /**
     * Get the value of the step slider given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    String stepSlider(String id );

    /**
     * Get the value of the slider given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    Float slider(String id );

    /**
     * Get the value of the input given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    String input(String id );

    /**
     * Get the value of the dropbox given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    String dropbox(String id );

}
