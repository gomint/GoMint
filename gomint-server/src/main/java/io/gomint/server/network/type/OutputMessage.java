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

    public String getFormat() {
        return format;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
