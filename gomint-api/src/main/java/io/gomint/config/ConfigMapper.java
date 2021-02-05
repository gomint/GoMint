/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config;

import io.gomint.config.annotation.Path;
import io.gomint.config.converter.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class ConfigMapper extends BaseConfigMapper {

    /**
     * Serialize this object to the field structure of the given class
     *
     * @param clazz which holds the fields to serialize
     * @return map containing all paths as keys and value
     * @throws Exception which can be everything
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> saveToMap(Class<?> clazz) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        if (!clazz.getSuperclass().equals(YamlConfig.class) && !clazz.getSuperclass().equals(Object.class)) {
            Map<String, Object> map = this.saveToMap(clazz.getSuperclass());

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                returnMap.put(entry.getKey(), entry.getValue());
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (this.prepareField(field)) {
                continue;
            }

            String path = this.getPath(field);

            try {
                returnMap.put(path, field.get(this));
            } catch (IllegalAccessException ignored) {
                // Ignored
            }
        }

        Converter mapConverter = this.converter.getConverter(Map.class);
        return (Map<String, Object>) mapConverter.toConfig(HashMap.class, returnMap, null);
    }

    /**
     * Deserialize the given map into the field structure of the given class
     *
     * @param section map containing all paths as keys and value
     * @param clazz which holds the field structure
     * @throws Exception which can be everything
     * @return config mapper for chaining
     */
    public ConfigMapper loadFromMap(Map<?, ?> section, Class<?> clazz) throws Exception {
        if (!clazz.getSuperclass().equals(YamlConfig.class)) {
            this.loadFromMap(section, clazz.getSuperclass());
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (this.prepareField(field)) {
                continue;
            }

            String path = this.getPath(field);
            this.converter.fromConfig((YamlConfig) this, field, ConfigSection.convertFromMap(section), path);
        }
        return this;
    }

    private boolean prepareField(Field field) {
        if (this.doSkip(field)) {
            return true;
        }

        if (Modifier.isPrivate(field.getModifiers())) {
            field.setAccessible(true);
        }

        return false;
    }

    private String getPath(Field field) {
        String path;

        switch (this.configMode) {
            case PATH_BY_UNDERSCORE:
                path = field.getName().replace("_", ".");
                break;
            case FIELD_IS_KEY:
                path = field.getName();
                break;
            case DEFAULT:
            default:
                if (field.getName().contains("_")) {
                    path = field.getName().replace("_", ".");
                } else {
                    path = field.getName();
                }

                break;
        }

        if (field.isAnnotationPresent(Path.class)) {
            path = field.getAnnotation(Path.class).value();
        }

        return path;
    }

}
