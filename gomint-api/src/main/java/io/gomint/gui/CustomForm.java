package io.gomint.gui;

import io.gomint.GoMint;
import io.gomint.gui.element.Dropdown;
import io.gomint.gui.element.StepSlider;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface CustomForm extends Form<FormResponse> {

    /**
     * Create a new custom form
     *
     * @param title of the form
     * @return fresh custom form
     */
    static CustomForm create( String title ) {
        return GoMint.instance().createCustomForm( title );
    }

    /**
     * Create a new dropbox
     *
     * @param id   of the dropbox ( important for the response )
     * @param text for display
     * @return new dropbox where you can add options
     */
    Dropdown createDropdown( String id, String text );

    /**
     * Add a new input box
     *
     * @param id           of the input ( important for the response )
     * @param text         for display
     * @param placeHolder  which should be put into the box
     * @param defaultValue of the input box
     * @return instance for chaining
     */
    CustomForm addInputField( String id, String text, String placeHolder, String defaultValue );

    /**
     * Add a new label
     *
     * @param text to display
     * @return instance for chaning
     */
    CustomForm addLabel( String text );

    /**
     * Add a range slider
     *
     * @param id           of the slider ( important for the response )
     * @param text         for display
     * @param min          value of the slider
     * @param max          value of the slider (when max lower min slider will not be added)
     * @param step         in which steps the client will select on this slider
     * @param defaultValue of this slider
     * @return instance for chaining
     */
    CustomForm addSlider( String id, String text, float min, float max, float step, float defaultValue );

    /**
     * Create a new step slider
     *
     * @param id   of the step slider ( important for the response )
     * @param text for display
     * @return new step slider where you can add steps
     */
    StepSlider createStepSlider( String id, String text );

    /**
     * Add a toggle
     *
     * @param id    of the toggle ( important for the response )
     * @param text  for display
     * @param value true when checked, false when not
     * @return instance for chaining
     */
    CustomForm addToggle( String id, String text, boolean value );

}
