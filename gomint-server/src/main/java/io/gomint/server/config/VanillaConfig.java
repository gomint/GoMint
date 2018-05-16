package io.gomint.server.config;

import io.gomint.config.Comment;
import io.gomint.config.YamlConfig;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class VanillaConfig extends YamlConfig {

    @Comment( "Disable the sprint reset when you hit something?")
    private boolean disableSprintReset = false;

}
