package io.gomint.server.network.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class OutputMessage {

    private String format;
    private boolean success;
    private List<String> parameters;

}
