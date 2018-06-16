package io.gomint.command.validator;

import com.google.common.base.Joiner;
import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnumValidator extends ParamValidator {

    private final List<String> values = new ArrayList<>();

    public EnumValidator( List<String> enumValues ) {
        if ( enumValues != null ) {
            this.values.addAll( enumValues );
        }
    }

    @Override
    public ParamType getType() {
        return ParamType.STRING_ENUM;
    }

    @Override
    public boolean hasValues() {
        return true;
    }

    @Override
    public List<String> values() {
        return this.values;
    }

    @Override
    public Object validate( List<String> input, CommandSender commandSender ) {
        String toCheck = input.get( 0 );
        if ( this.values.contains( toCheck ) ) {
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
        return Joiner.on( " | " ).join( this.values );
    }

}
