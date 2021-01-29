/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.plugin;

import io.gomint.config.InvalidConfigurationException;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.event.EventPriority;
import io.gomint.event.world.WorldLoadEvent;
import io.gomint.event.world.WorldUnloadEvent;
import io.gomint.plugin.Plugin;
import io.gomint.world.World;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class PluginWorldConfigManager implements EventListener {

    private final SimplePluginManager pluginManager;

    PluginWorldConfigManager(SimplePluginManager pluginManager) {
        this.pluginManager = pluginManager;
        pluginManager.eventManager().registerListener(this);
    }

    void onPluginLoad(PluginMeta meta, Plugin plugin) throws InvalidConfigurationException {
        PluginWorldConfig cfg = new PluginWorldConfig();
        cfg.load(new File(plugin.dataFolder(), "worlds.yml"));
        meta.pluginWorldConfig(cfg);
        if (!cfg.worldList.isEmpty()) {
            meta.activeWorlds(ConcurrentHashMap.newKeySet());
        }
        this.pluginManager.server().worlds().forEach(world -> {
            handleWorldLoad(world, meta);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onWorldLoad(WorldLoadEvent event) {
        this.pluginManager.pluginMetadatas().values().forEach(meta -> handleWorldLoad(event.world(), meta));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onWorldUnload(WorldUnloadEvent event) {
        this.pluginManager.pluginMetadatas().values().forEach(meta -> handleWorldUnload(event.world(), meta));
    }

    private void handleWorldLoad(World world, PluginMeta meta) {
        PluginWorldConfig cfg = meta.pluginWorldConfig();
        if (!cfg.isBlockList ^ cfg.worldList.contains(world.folder())) {
            ConcurrentHashMap.KeySetView<String, Boolean> activeWorlds = meta.activeWorlds();
            if (activeWorlds != null) {
                activeWorlds.add(world.folder());
            }
        }
    }

    private void handleWorldUnload(World world, PluginMeta pluginMeta) {
        PluginWorldConfig cfg = pluginMeta.pluginWorldConfig();
        if (!cfg.isBlockList ^ cfg.worldList.contains(world.folder())) {
            ConcurrentHashMap.KeySetView<String, Boolean> activeWorlds = pluginMeta.activeWorlds();
            if (activeWorlds != null) {
                activeWorlds.remove(world.folder());
            }
        }
    }

}
