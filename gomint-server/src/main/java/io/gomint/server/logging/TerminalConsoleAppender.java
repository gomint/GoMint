package io.gomint.server.logging;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;

/**
 * @author geNAZt
 * @version 1.0
 */
@Plugin(name = "TerminalConsole", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public final class TerminalConsoleAppender extends AbstractAppender {

    // Grab early or we will infinite loop with the log4j2 stdout redirection
    private static final PrintStream STDOUT = System.out;

    private static boolean initialized;
    private static Terminal terminal;
    private static LineReader reader;

    private TerminalConsoleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
        initializeTerminal();
    }

    private static void initializeTerminal() {
        if (!initialized) {
            initialized = true;

            // boolean dumb = System.getProperty("java.class.path").contains("idea_rt.jar"); // TODO: Check if other IDEs also have virtual terminals with ANSI color support

            try {
                terminal = TerminalBuilder.builder().dumb(false).build();
            } catch (IllegalStateException e) {
                LOGGER.warn("Not supported terminal");
            } catch (IOException e) {
                LOGGER.error("Failed to init, falling back to STDOUT");
                LOGGER.debug(e);
            }
        }
    }

    @Override
    public void append(LogEvent event) {
        if (terminal != null) {
            if (reader != null) {
                try {
                    // Clear, write and redraw prompt again so the prompt is always at the bottom
                    reader.callWidget(LineReader.CLEAR);
                    terminal.writer().print(getLayout().toSerializable(event));
                    reader.callWidget(LineReader.REDRAW_LINE);
                    reader.callWidget(LineReader.REDISPLAY);
                } catch (Exception e) {
                    // There was an error (we did not really read from terminal)
                    terminal.writer().print(getLayout().toSerializable(event));
                }
            } else {
                // There is no reader, no need to redraw prompt
                terminal.writer().print(getLayout().toSerializable(event));
            }

            terminal.writer().flush();
        } else {
            STDOUT.print(getLayout().toSerializable(event));
        }
    }

    /**
     * Close the jLine terminal when needed
     *
     * @throws IOException when closing failed
     */
    public static void close() throws IOException {
        if (terminal != null) {
            terminal.reader().shutdown();

            /*try {
                terminal.close();
            } finally {
                terminal = null;
            }*/
        }
    }

    /**
     * Factory for log4j2
     *
     * @param name             of the appender
     * @param filter           for the appender
     * @param layout           for the appender
     * @param ignoreExceptions controls if we should display exceptions
     * @return new jline terminal appender
     */
    @PluginFactory
    public static TerminalConsoleAppender createAppender(
        @Required(message = "No name provided for TerminalConsoleAppender") @PluginAttribute("name") String name,
        @PluginElement("Filter") Filter filter,
        @PluginElement("Layout") @Nullable Layout<? extends Serializable> layout,
        @PluginAttribute(value = "ignoreExceptions", defaultBoolean = true) boolean ignoreExceptions) {

        Layout<? extends Serializable> finalLayout = layout;
        if (layout == null) {
            finalLayout = PatternLayout.createDefaultLayout();
        }

        return new TerminalConsoleAppender(name, filter, finalLayout, ignoreExceptions);
    }

    /**
     * Return the build up jLine terminal or null when the init failed
     *
     * @return jLine terminal or null
     */
    public static Terminal getTerminal() {
        return terminal;
    }

    /**
     * Set a new jLine reader. Readers are used to parse stdin
     *
     * @param newReader The new line reader
     */
    public static void setReader(LineReader newReader) {
        if (newReader != null && newReader.getTerminal() != terminal) {
            throw new IllegalArgumentException("Reader was not created with TerminalConsoleAppender.getTerminal()");
        }

        reader = newReader;
    }

}
