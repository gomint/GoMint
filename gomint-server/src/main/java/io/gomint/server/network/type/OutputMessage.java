package io.gomint.server.network.type;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class OutputMessage {

    private String format;
    private boolean success;
    private List<String> parameters;

    public OutputMessage(String format, boolean success, List<String> parameters) {
        this.format = format;
        this.success = success;
        this.parameters = parameters;
    }

    public String format() {
        return this.format;
    }

    public boolean success() {
        return this.success;
    }

    public List<String> parameters() {
        return this.parameters;
    }
}
