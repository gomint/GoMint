/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author geNAZt, derklaro
 * @version 1.0
 * @stability 3
 */
public class CommandOutput {

    /**
     * Creates a new command output which indicates that the command was processed successfully. By default
     * this command output has no result messages - if you want to add a success message you should use
     * {@link #successful(String, Object...)} instead.
     *
     * @return A new successful command output
     * @since 1.0.0-RC4
     */
    public static CommandOutput successful() {
        return new CommandOutput(true);
    }

    /**
     * Creates a new command output which indicates that the command was processed successfully. By default
     * this command output has a result message created with the given string pattern provided by {@code message}
     * and formatted using the given {@code params} parameters.
     *
     * @param message The string format of the success result message.
     *                See <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html">Formatter documentation</a> for more details.
     * @param params  The parameters for the string format in the correct order
     * @return A new successful command output with the given formatted result message in it
     * @since 1.0.0-RC4
     */
    public static CommandOutput successful(String message, Object... params) {
        return new CommandOutput(true).success(message, params);
    }

    /**
     * Creates a new command output which indicates that the command was processed with a failure. By default
     * this command output has no result messages - if you want to add a message why the command execution failed exactly
     * you should use {@link #failure(String, Object...)} or {@link #failure(Throwable)} instead.
     *
     * @return A new failed command output
     * @since 1.0.0-RC4
     */
    public static CommandOutput failure() {
        return new CommandOutput(false);
    }

    /**
     * Creates a new command output which indicates that the command was processed with a failure. By default
     * this command output has a result message created with the given string pattern provided by {@code message}
     * and formatted using the given {@code params} parameters. If you want to submit a stack trace of an exception
     * you should use {@link #failure(Throwable)}.
     *
     * @param message The string format of the success result message.
     *                See <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html">Formatter documentation</a> for more details.
     * @param params  The parameters for the string format in the correct order
     * @return A new failed command output with the given formatted result message in it
     * @since 1.0.0-RC4
     */
    public static CommandOutput failure(String message, Object... params) {
        return new CommandOutput(true).fail(message, params);
    }

    /**
     * Creates a new command output which indicates that the command was processed with a failure. By default
     * this command output has a message in it created from the given throwable which is formatted in the following
     * way: {@code ExceptionClassName: ExceptionMessage} followed by nothing if the exception has no stack trace elements
     * or the first trace element formatted like: {@code @ TraceClassName:TraceLineNumber}.
     *
     * @param throwable The exception which occurred during the command execution
     * @return A new failed command output with the formatted throwable as it's failure reason message
     * @since 1.0.0-RC4
     */
    public static CommandOutput failure(Throwable throwable) {
        StackTraceElement[] trace = throwable.getStackTrace();
        String firstLine = trace.length > 0 ? " @ " + trace[0].getClassName() + ":" + trace[0].getLineNumber() : "";
        return new CommandOutput(false).fail("%s: %s%s", throwable.getClass().getSimpleName(), throwable.getMessage(), firstLine);
    }

    /**
     * Creates a new command output. Will be private in a further release
     *
     * @deprecated Use {@link #successful()}, {@link #successful(String, Object...)}, {@link #failure()} or {@link #failure(String, Object...)} instead.
     */
    @Deprecated(since = "1.0.0-RC4")
    public CommandOutput() {
    }

    private CommandOutput(boolean success) {
        this.success = success;
    }

    private boolean success = true;
    private final Collection<CommandOutputMessage> messages = new ArrayList<>();

    /**
     * When the execution of a command failed you can execute this and must provide a reason why it failed
     *
     * @param format of the fail reason
     * @param params which should be filled into the given reason
     * @return this command output instance for chaining
     */
    public CommandOutput fail(String format, Object... params) {
        String[] output = this.remap(params);
        this.messages.add(new CommandOutputMessage(false, format, Arrays.asList(output)));
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
     * @return this command output instance for chaining
     */
    public CommandOutput success(String format, Object... params) {
        String[] output = this.remap(params);
        this.messages.add(new CommandOutputMessage(true, format, Arrays.asList(output)));
        return this;
    }

    /**
     * Indicates if the current output is successful or not.
     *
     * @return if the current output is successful or not.
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * Contains all messages which are sent to the command sender. This will result in an
     * unmodifiable copy of the collection - you should use {@link #success(String, Object...)} or
     * {@link #fail(String, Object...)} for adding messages to the collection instead.
     *
     * @return all messages which are sent to the command sender.
     */
    public Collection<CommandOutputMessage> getMessages() {
        return Collections.unmodifiableCollection(this.messages);
    }

    private String[] remap(Object[] params) {
        String[] stringParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            stringParams[i] = String.valueOf(params[i]);
        }

        return stringParams;
    }
}
