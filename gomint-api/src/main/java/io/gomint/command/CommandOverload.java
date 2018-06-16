package io.gomint.command;

import io.gomint.command.validator.CommandValidator;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@ToString
public class CommandOverload {

    @Getter
    private Map<String, ParamValidator> parameters;

    /**
     * Add a param to the command. Params are passed to their validators when the command gets
     * executed.
     *
     * @param name      of the parameter
     * @param validator which should decide if the parameter is valid
     * @return the command currently build
     */
    public CommandOverload param( String name, ParamValidator validator ) {
        if ( this.parameters == null ) {
            this.parameters = new LinkedHashMap<>();
        }

        // Special case CommandValidator
        if ( validator instanceof CommandValidator ) {
            validator.values().add( name );
        }

        this.parameters.put( name, validator );
        return this;
    }

    /**
     * Add a param to the command. Params are passed to their validators when the command gets
     * executed.
     *
     * @param name      of the parameter
     * @param validator which should decide if the parameter is valid
     * @param optional  true when parameter is optional, false when not
     * @return the command currently build
     */
    public CommandOverload param( String name, ParamValidator validator, boolean optional ) {
        if ( this.parameters == null ) {
            this.parameters = new LinkedHashMap<>();
        }

        // Special case CommandValidator
        if ( validator instanceof CommandValidator ) {
            validator.values().add( name );
        }

        validator.setOptional( optional );
        this.parameters.put( name, validator );
        return this;
    }

    /**
     * Return the amount of optional parts in this overload
     *
     * @return amount of optionals
     */
    public int sizeOfOptionals() {
        int count = 0;

        for ( ParamValidator validator : this.parameters.values() ) {
            if ( validator.isOptional() ) {
                count++;
            }
        }

        return count;
    }

}
