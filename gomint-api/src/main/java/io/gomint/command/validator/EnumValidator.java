/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command.validator;

import com.google.common.base.Joiner;
import io.gomint.command.CommandSender;
import io.gomint.command.ParamType;
import io.gomint.command.ParamValidator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class EnumValidator extends ParamValidator {

    private final List<String> values = new ArrayList<>();

    public EnumValidator( List<String> enumValues ) {
        if ( enumValues != null ) {
            this.values.addAll( enumValues );
        }
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
    public Object validate( String input, CommandSender commandSender ) {
        if ( this.values.contains( input ) ) {
            return input;
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamType getType() {
        return ParamType.STRING_ENUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> values() {
        return this.values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasValues() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHelpText() {
        return Joiner.on( " | " ).join( this.values );
    }

}
