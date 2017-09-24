package io.gomint.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class CommandOutputMessage {

    private boolean success;
    private String format;
    private List<String> parameters;

}
