package io.gomint.command.validator;

import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;
import io.gomint.entity.Entity;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StringValidator extends ParamValidator {

    private final Pattern pattern;

    public StringValidator( String regex ) {
        this.pattern = Pattern.compile( regex );
    }

    @Override
    public ParamType getType() {
        return ParamType.STRING;
    }

    @Override
    public boolean hasValues() {
        return false;
    }

    @Override
    public List<String> values() {
        return null;
    }

    @Override
    public Object validate( List<String> input, CommandSender commandSender ) {
        String toCheck = input.get( 0 );
        if ( this.pattern.matcher( toCheck ).matches() ) {
            return toCheck;
        }

        return null;
    }

    @Override
    public int consumesParts() {
        return 1;
    }

    @Override
    public String getHelpText() {
        return "string:" + this.pattern.pattern();
    }

}
