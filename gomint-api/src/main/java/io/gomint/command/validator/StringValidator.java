/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command.validator;

import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class StringValidator extends ParamValidator {

    private final Pattern pattern;

    public StringValidator( String regex ) {
        this.pattern = Pattern.compile( regex );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object validate( String input, CommandSender commandSender ) {
        if ( this.pattern.matcher( input ).matches() ) {
            return input;
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String consume( Iterator<String> data ) {
        if ( data.hasNext() ) {
            return data.next();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamType getType() {
        return ParamType.STRING;
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
        return "string:" + this.pattern.pattern();
    }

}
