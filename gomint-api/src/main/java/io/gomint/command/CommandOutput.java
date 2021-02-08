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
import java.util.function.Consumer;

/**
 * All output from a command should be collected in the provided {@link CommandOutput} instance so the client can
 * process it in the proper manner. Sending chat messages for the command result is NOT recommended and should NEVER be
 * done.
 * <br><br>
 * When you want to asynchroniously finish command execution and then send messages to the executor, you need to call
 * {@linkplain #markAsync()} synchroniously and when your async processing is done, call {@linkplain #markFinished()}
 * asynchroniously.
 *
 * @author geNAZt, derklaro
 * @version 1.0
 * @stability 3
 * @see #markFinished()
 */
public class CommandOutput {

    private boolean success = true;
    private final Collection<CommandOutputMessage> messages = Collections.synchronizedCollection(new ArrayList<>());
    private final Consumer<CommandOutput> outputConsumer;
    private volatile boolean async = false;
    private volatile boolean done = false;

    private CommandOutput(Consumer<CommandOutput> outputConsumer) {
        this.outputConsumer = outputConsumer;
    }

    /**
     * When the execution of a command failed you can execute this. This adds a result message created with the given
     * string pattern provided by {@code message} and formatted using the given {@code params} parameters. If you want
     * to submit a stack trace of an exception you should use {@link #fail(Throwable)}.
     *
     * @param format The string format of the fail result message.
     *               See <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html">Formatter documentation</a> for more details.
     * @param params The parameters for the string format in the correct order
     * @return this command output instance for chaining
     * @see #fail(Throwable)
     */
    public CommandOutput fail(String format, Object... params) {
        this.success = false;
        String[] output = this.remap(params);
        this.messages.add(new CommandOutputMessage(false, format, Arrays.asList(output)));
        return this;
    }

    /**
     * When the execution of a command failed you can execute this. This adds a result message created from the given
     * throwable which is formatted in the following way: {@code ExceptionClassName: ExceptionMessage} followed by
     * nothing if the exception has no stack trace elements or the first trace element formatted like:
     * {@code @ TraceClassName:TraceLineNumber}.
     *
     * @param throwable The exception which occurred during the command execution
     * @return this command output instance for chaining
     * @see #fail(String, Object...)
     * @since 1.0.0-RC4
     */
    public CommandOutput fail(Throwable throwable) {
        this.success = false;
        StackTraceElement[] trace = throwable.getStackTrace();
        String firstLine = trace.length > 0 ? " @ " + trace[0].getClassName() + ":" + trace[0].getLineNumber() : "";
        return fail("%s: %s%s", throwable.getClass().getSimpleName(), throwable.getMessage(), firstLine);
    }

    /**
     * When the execution of the command resulted in a success, you can append a message here. This adds a result
     * message created with the given string pattern provided by {@code message} and formatted using the given
     * {@code params} parameters
     *
     * @param format The string format of the success result message.
     *               See <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html">Formatter documentation</a> for more details.
     * @param params The parameters for the string format in the correct order
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
    public boolean success() {
        return this.success;
    }

    /**
     * Contains all messages which are sent to the command sender. The returned collection is not modifiable.
     *
     * Use @linkplain #success(String, Object...)}, {@linkplain #fail(String, Object...)} or
     * {@linkplain #fail(Throwable)} to add messages to the output.
     *
     * @return all messages which are sent to the command sender.
     */
    public Collection<CommandOutputMessage> messages() {
        return Collections.unmodifiableCollection(this.messages);
    }

    private String[] remap(Object[] params) {
        String[] stringParams = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            stringParams[i] = String.valueOf(params[i]);
        }

        return stringParams;
    }

    /**
     * When the command will finish asynchroniously, you need to call this method so the CommandOUtput will wait for
     * {@linkplain #markFinished()} call and only then send the CommandOutput to the executor.
     * @return this command output instance for chaining
     * @see #markFinished() 
     * @see #isAsync() 
     */
    public CommandOutput markAsync() {
        this.async = true;
        return this;
    }

    /**
     * When {@linkplain #markAsync()} has been called on this CommandOutput, you need to call this method to mark the
     * end of the command execution
     * @see #markAsync() 
     * @see #isFinished() 
     */
    public synchronized void markFinished() {
        if (!this.done) {
            this.done = true;
            this.outputConsumer.accept(this);
        }
    }

    /**
     * Checks whether the command execution will finish asynchroniously
     *
     * @return whether the command execution will finish asynchroniously
     * @see #markAsync() 
     */
    public boolean isAsync() {
        return this.async;
    }

    /**
     * Checks whether the command execution has finished
     *
     * @return whether the command execution has finished
     * @see #markFinished() 
     */
    public boolean isFinished() {
        return this.done;
    }

}
