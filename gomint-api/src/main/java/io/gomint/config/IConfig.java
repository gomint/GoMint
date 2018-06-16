package io.gomint.config;

import java.io.File;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface IConfig {

    public void save() throws InvalidConfigurationException;

    public void save( File file ) throws InvalidConfigurationException;

    public void init() throws InvalidConfigurationException;

    public void init( File file ) throws InvalidConfigurationException;

    public void reload() throws InvalidConfigurationException;

    public void load() throws InvalidConfigurationException;

    public void load( File file ) throws InvalidConfigurationException;

}
