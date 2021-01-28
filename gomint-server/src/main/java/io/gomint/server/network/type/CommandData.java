package io.gomint.server.network.type;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CommandData {

    private final String name;
    private final String description;
    private byte flags;
    private byte permission;
    private int aliasIndex = -1; // TODO: Unused due to 1.2 alias bug
    private List<List<Parameter>> parameters;

    public CommandData(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public void setPermission(byte permission) {
        this.permission = permission;
    }

    public void setAliasIndex(int aliasIndex) {
        this.aliasIndex = aliasIndex;
    }

    public void setParameters(List<List<Parameter>> parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public byte getFlags() {
        return this.flags;
    }

    public byte getPermission() {
        return this.permission;
    }

    public int getAliasIndex() {
        return this.aliasIndex;
    }

    public List<List<Parameter>> getParameters() {
        return this.parameters;
    }

    public static class Parameter {
        private final String name;
        private final int type;
        private final boolean optional;

        public Parameter(String name, int type, boolean optional) {
            this.name = name;
            this.type = type;
            this.optional = optional;
        }

        public String getName() {
            return this.name;
        }

        public int getType() {
            return this.type;
        }

        public boolean isOptional() {
            return this.optional;
        }
    }

}
