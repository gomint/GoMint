package io.gomint.server.config;

import io.gomint.config.annotation.Comment;
import io.gomint.config.YamlConfig;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ConnectionConfig extends YamlConfig {

    @Comment("Enable connection encryption. It uses AES-128 so it costs a bit of CPU power")
    private boolean enableEncryption = true;

    @Comment("Level of compression used for packets. Lower = less CPU / higher traffic; Higher = more CPU / lower traffic")
    private int compressionLevel = 7;

    public boolean isEnableEncryption() {
        return enableEncryption;
    }

    public int getCompressionLevel() {
        return compressionLevel;
    }

}
