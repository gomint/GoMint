package io.gomint.server.entity.ai;

/**
 * An AIStateMachine's task is to control the execution flow of different AI states
 * attached to it. It is responsible for updating the current AI state in order to
 * allow it to make changes to any entity it is associated with.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class AIStateMachine {

    private AIState activeState;

    /**
     * Gets the AI state that is currently active.
     *
     * @return The currently active AI state
     */
    public AIState getActiveState() {
        return this.activeState;
    }

    /**
     * Propagates the given AI event to the currently active AI state.
     *
     * @param event The event to propagate
     */
    public void propagateEvent( AIEvent event ) {
        if ( this.activeState != null ) {
            this.activeState.onEvent( event );
        }
    }

    /**
     * Switches the currently active AI state. If next is set to null
     * this function will behave exactly as {@link #stopExecution()}.
     * In order to get the first AI state running the creator of the
     * state machine will have to invoke this method handing in the
     * initially active state.
     *
     * @param next The state to switch in next
     */
    public void switchState( AIState next ) {
        if ( this.activeState != null ) {
            this.activeState.onLeave();
        }

        AIState old = this.activeState;
        this.activeState = next;

        if ( this.activeState != null ) {
            this.activeState.onEnter(old);
        }
    }

    /**
     * Stops the execution of any AI state that is currently active.
     */
    public void stopExecution() {
        this.switchState( null );
    }

    /**
     * Updates the state machine and the currently active AI state.
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last update in seconds
     */
    public void update( long currentTimeMS, float dT ) {
        if ( this.activeState != null ) {
            this.activeState.update( currentTimeMS, dT );
        }
    }

}
