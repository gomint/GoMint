package io.gomint.server.config;

import io.gomint.config.annotation.Comment;
import io.gomint.config.YamlConfig;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ConnectionConfig extends YamlConfig {

    @Comment("Root of certification chain used for JWT")
    private String jwtRoot = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkix"
        + "yLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f"
        + "/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFS"
        + "NBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V";

    public String jwtRoot() {
        return this.jwtRoot;
    }

}
