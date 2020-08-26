/*
 * Copyright (c) 2018 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class CommandOutput {

    private boolean success = true;
    private List<CommandOutputMessage> messages = new ArrayList<>();

    public boolean isSuccess() {
        return success;
    }

    public List<CommandOutputMessage> getMessages() {
        return messages;
    }

    /**
     * When the execution of a command failed you can execute this and must provide a reason why it failed
     *
     * @param format of the fail reason
     * @param params which should be filled into the given reason
     * @return this command output instance
     */
    public CommandOutput fail( String format, Object... params ) {
        String[] output = remap( params );
        this.messages.add( new CommandOutputMessage( false, format, Arrays.asList( output ) ) );
        this.success = false;
        return this;
    }

    /**
     * When the execution of the command resulted in a success operation you can append a message here. All
     * output from a command should be collected in a {@link CommandOutput} instance so the client can process it
     * in the proper manner. Sending chat messages for the command result is NOT recommended and should NEVER be done
     *
     * @param format of the success message
     * @param params to pass into the given format
     * @return this command output instance
     */
    public CommandOutput success( String format, Object... params ) {
        String[] output = remap( params );
        this.messages.add( new CommandOutputMessage( true, format, Arrays.asList( output ) ) );
        return this;
    }

    private String[] remap( Object[] params ) {
        String[] stringParams = new String[params.length];
        for ( int i = 0; i < params.length; i++ ) {
            stringParams[i] = String.valueOf( params[i] );
        }

        return stringParams;
    }

}
