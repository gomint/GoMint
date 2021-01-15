package io.gomint.addons.components;

/**
 * An interface for classes whose instances may be extended by components.
 *
 * Any components bound to an instance can be retrieved by specifying their
 * component class.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability 1
 */
public interface ComponentHolder {

    /**
     * Retrieves a typed component, if found on the instance.
     *
     * @param cla The class of the component to retrieve
     * @param <C> The class of the component to retrieve
     * @return The component if found or null otherwise
     */
    <C extends Component> C getComponent(Class<C> cla);

}
