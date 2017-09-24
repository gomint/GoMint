package io.gomint.command.validator;

import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;
import io.gomint.entity.Entity;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author geNAZt
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
    public Object validate( List<String> input, Entity entity ) {
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

}
