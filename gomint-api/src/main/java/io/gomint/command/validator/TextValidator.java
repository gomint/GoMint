package io.gomint.command.validator;

import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;

import java.util.Iterator;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TextValidator extends ParamValidator {

    /**
     * {@inheritDoc}
     */
    @Override
    public String consume( Iterator<String> data ) {
        StringBuilder forValidator = new StringBuilder();
        while ( data.hasNext() ) {
            forValidator.append( data.next() ).append( " " );
        }

        if ( forValidator.length() > 0 ) {
            return forValidator.deleteCharAt( forValidator.length() - 1 ).toString();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object validate( String input, CommandSender commandSender ) {
        return input;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamType getType() {
        return ParamType.TEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> values() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasValues() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHelpText() {
        return "text";
    }

}
