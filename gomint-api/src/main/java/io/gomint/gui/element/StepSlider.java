package io.gomint.gui.element;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface StepSlider {

    /**
     * Add a new step
     *
     * @param step to add
     * @return StepSlider instance for chaining
     */
    StepSlider addStep( String step );

    /**
     * Add a new step with the option to select this as default
     *
     * @param step        to add
     * @param defaultStep true when this should be default, false when not
     * @return StepSlider instance for chaining
     */
    StepSlider addStep( String step, boolean defaultStep );

}
