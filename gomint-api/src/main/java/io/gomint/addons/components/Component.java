package io.gomint.addons.components;

/**
 * A component that can be attached to a declaration of some resource.
 *
 * Components describe properties of a declared resource such as a block or item.
 * For example, blocks can be given a component named 'minecraft:friction' which
 * describes a single, decimal value that is applied as friction when walking on
 * the respective block.
 *
 * Components have namespaces: in the example above the described component's
 * namespace would be 'minecraft'. The namespace 'minecraft' is reserved by
 * Mojang and must not be used by addons to create custom components. Since
 * the components within this namespace are standardized by Mojang, they are
 * usually not exposed through this class but are rather integrated into the
 * respective interface of Blocks, Items, etc. for easier access. For purposes
 * of future integrations, the namespace 'gomint' is also reserved when using
 * addons with the GoMint server software.
 * However, as developers can create custom components those components must
 * be made accessible as well.
 *
 * This interface should be extended to create custom component classes.
 * Additionally, they should be annotated and later registered so they can
 * be recognized when imported later.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability 1
 */
public interface Component {



}
