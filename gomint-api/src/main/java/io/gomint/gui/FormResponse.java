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
    Boolean getToggle( String id );

    /**
     * Get the value of the step slider given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    String getStepSlider( String id );

    /**
     * Get the value of the slider given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    Float getSlider( String id );

    /**
     * Get the value of the input given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    String getInput( String id );

    /**
     * Get the value of the dropbox given
     *
     * @param id for which we need the value
     * @return null when not found or the value given from the client
     */
    String getDropbox( String id );

}
