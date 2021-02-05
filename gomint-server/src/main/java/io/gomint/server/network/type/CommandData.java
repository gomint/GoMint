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

    public void flags(byte flags) {
        this.flags = flags;
    }

    public void permission(byte permission) {
        this.permission = permission;
    }

    public void aliasIndex(int aliasIndex) {
        this.aliasIndex = aliasIndex;
    }

    public void parameters(List<List<Parameter>> parameters) {
        this.parameters = parameters;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public byte flags() {
        return this.flags;
    }

    public byte permission() {
        return this.permission;
    }

    public int aliasIndex() {
        return this.aliasIndex;
    }

    public List<List<Parameter>> parameters() {
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

        public String name() {
            return this.name;
        }

        public int type() {
            return this.type;
        }

        public boolean optional() {
            return this.optional;
        }
    }

}
