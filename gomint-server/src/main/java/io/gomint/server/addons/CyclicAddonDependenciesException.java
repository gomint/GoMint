package io.gomint.server.addons;

/**
 * An exception indicating that there are cyclic dependencies in a set of addons.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability experimental
 */
public class CyclicAddonDependenciesException extends Exception {

    public CyclicAddonDependenciesException() {
        super("There are cyclic dependencies in the given set of addons");
    }

}
