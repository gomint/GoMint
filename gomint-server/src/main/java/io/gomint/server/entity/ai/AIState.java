package io.gomint.server.entity.ai;

/**
 * Base class for all AI states. Defines some basic methods common to all AI states.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public abstract class AIState {

    // The state machine this AIState belongs to:
    private final AIStateMachine machine;

    /**
     * Constructs a new AIState that will belong to the given state machine.
     *
     * @param machine The state machine the AIState being constructed belongs to
     */
    protected AIState( AIStateMachine machine ) {
        this.machine = machine;
    }

    /**
     * Checks whether or not the state is currently active.
     *
     * @return Whether or not the state is currently active
     */
    public boolean isActive() {
        return ( this.machine.getActiveState() == this );
    }

    public void switchState(AIState state) {
        this.machine.switchState(state);
    }

    /**
     * Gets called when another AIState wants to notify you for something
     *
     * @param event The event which has been created
     */
    public void onEvent( AIEvent event ) {

    }

    /**
     * Updates the AI state. Will be invoked by the AIStateMachine the state is added to
     * whenever this state is currently active.
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last update tick in seconds
     */
    protected void update( long currentTimeMS, float dT ) {

    }

    /**
     * Invoked by the state machine whenever the state is entered.
     *
     * @param oldState which was given before, can be null if this is the first state
     */
    protected void onEnter(AIState oldState) {

    }

    /**
     * Invoked by the state machine whenever the state is left.
     */
    protected void onLeave() {

    }

}
