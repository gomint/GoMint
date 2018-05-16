package io.gomint.server.maintenance.performance;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PeformanceReport {

    private boolean enabled;

    /**
     * Generate a new PerformanceReport
     *
     * @param enabled Default enabled or not
     */
    public PeformanceReport( boolean enabled ) {
        this.enabled = enabled;
    }

    /**
     * Start a new tick
     *
     * @param currentTimeMillis when the tick started
     */
    public void startTick( long currentTimeMillis ) {
        // Fast out
        if ( !this.enabled ) {
            return;
        }


    }

}
