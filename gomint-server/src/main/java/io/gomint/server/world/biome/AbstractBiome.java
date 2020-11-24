/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.biome;

import com.google.common.collect.Sets;
import io.gomint.server.world.biome.component.Component;
import io.gomint.world.biome.Biome;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AbstractBiome implements Biome {

    private final Object2ObjectMap<Class<?>, Object> components = new Object2ObjectOpenHashMap<>();
    private Set<String> tags;

    /**
     * New biome with pre constructed components
     *
     * @param components which this biome should have
     */
    public AbstractBiome(Component ... components) {
        for (Component component : components) {
            this.components.put(component.getClass(), component);

            for (Class<?> interfaz : component.getClass().getInterfaces()) {
                if (!Component.class.equals(interfaz)) {
                    this.components.put(interfaz, component);

                }
            }
        }
    }

    public void tags(String ... tags) {
        this.tags = Sets.newHashSet(tags);
    }

    @Override
    public <Component> Component component(Class<Component> componentClass) {
        return (Component) this.components.get(componentClass);
    }

    @Override
    public Set<String> tags() {
        return this.tags;
    }

}
