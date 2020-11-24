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
import io.gomint.command.PlayerCommandSender;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.BlockPosition;

import java.util.Iterator;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class BlockPositionValidator extends ParamValidator {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object validate( String input, CommandSender sender ) {
        // 0 -> x
        // 1 -> y
        // 2 -> z

        BlockPosition entityPosition = new BlockPosition(0, 0, 0);
        if ( sender instanceof PlayerCommandSender ) {
            // Mojang decided that ~ is the current entity position
            entityPosition = ( (EntityPlayer) sender ).getLocation().toBlockPosition();
        }

        // Split string
        String[] split = input.split( " " );

        // Parse x
        Integer xInt = parsePos( entityPosition.getX(), split[0] );
        if ( xInt == null ) {
            return null;
        }

        Integer yInt = parsePos( entityPosition.getY(), split[1] );
        if ( yInt == null ) {
            return null;
        }

        Integer zInt = parsePos( entityPosition.getZ(), split[2] );
        if ( zInt == null ) {
            return null;
        }

        return new BlockPosition( xInt, yInt, zInt );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String consume( Iterator<String> data ) {
        StringBuilder forValidator = new StringBuilder();
        for ( int i = 0; i < 3; i++ ) {
            if ( !data.hasNext() ) {
                return null;
            }

            forValidator.append( data.next() ).append(" ");
        }

        return forValidator.deleteCharAt(forValidator.length() - 1).toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamType getType() {
        return ParamType.BLOCK_POS;
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
        return "blockpos:x y z";
    }

    private Integer parsePos( int positionValue, String in ) {
        if ( in.startsWith( "~" ) && positionValue != 0 ) {
            // Do we have additional data (+/-)?
            if ( in.length() > 2 ) {
                if ( in.startsWith( "~+" ) ) {
                    try {
                        int diffX = Integer.parseInt( in.substring( 2 ) );
                        positionValue += diffX;
                        return positionValue;
                    } catch ( NumberFormatException e ) {
                        return null;
                    }
                } else if ( in.startsWith( "~-" ) ) {
                    try {
                        int diffX = Integer.parseInt( in.substring( 2 ) );
                        positionValue -= diffX;
                        return positionValue;
                    } catch ( NumberFormatException e ) {
                        return null;
                    }
                }
            } else {
                try {
                    int diffX = Integer.parseInt( in.substring( 1 ) );
                    positionValue += diffX;
                    return positionValue;
                } catch ( NumberFormatException e ) {
                    return null;
                }
            }
        } else {
            try {
                return Integer.parseInt( in );
            } catch ( NumberFormatException e ) {
                return null;
            }
        }

        return null;
    }

}
