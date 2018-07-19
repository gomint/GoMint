/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import com.google.common.base.Strings;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class DumpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger( DumpUtil.class );

    public static void dumpPacketbuffer( PacketBuffer buffer ) {
        StringBuilder lineBuilder = new StringBuilder();
        StringBuilder stringRepBuilder = new StringBuilder();
        while ( buffer.getRemaining() > 0 ) {
            for ( int i = 0; i < 64 && buffer.getRemaining() > 0; ++i ) {
                byte b = buffer.readByte();
                String hex = Integer.toHexString( ( (int) b ) & 0xFF );
                if ( hex.length() < 2 ) {
                    hex = "0" + hex;
                }

                stringRepBuilder.append( (char) ( b & 0xFF ) );
                lineBuilder.append( hex );
                if ( i + 1 < 64 && buffer.getRemaining() > 0 ) {
                    lineBuilder.append( " " );
                }
            }

            lineBuilder.append( " " ).append( stringRepBuilder );

            LOGGER.info( lineBuilder.toString() );
            lineBuilder = new StringBuilder();
            stringRepBuilder = new StringBuilder();
        }

        buffer.resetPosition();
    }

    public static void dumpByteArray( byte[] bytes, int skip ) {
        int count = 0;
        int total = 0;
        StringBuilder stringBuilder = new StringBuilder( "\n 00000000: " );

        int skipped = 0;
        for ( byte aByte : bytes ) {
            if ( skipped++ < skip ) {
                continue;
            }

            String hex = Integer.toHexString( aByte & 255 );
            if ( hex.length() == 1 ) {
                hex = "0" + hex;
            }

            stringBuilder.append( hex ).append( " " );
            total++;

            if ( ++count == 32 ) {
                StringBuilder intDisplay = new StringBuilder( Integer.toString( total ) );
                int missingTrailing = 8 - intDisplay.length();
                for ( int i = 0; i < missingTrailing; i++ ) {
                    intDisplay.insert( 0, "0" );
                }

                stringBuilder.append( " " ).append( "\n " ).append( intDisplay ).append( ": " );
                count = 0;
            }
        }

        LOGGER.info( stringBuilder.toString() );
    }

    public static void dumpByteArray( byte[] bytes ) {
        int count = 0;
        int total = 0;

        StringBuilder stringBuilder = new StringBuilder( "\n 00000000: " );

        for ( byte aByte : bytes ) {
            String hex = Integer.toHexString( aByte & 255 );
            if ( hex.length() == 1 ) {
                hex = "0" + hex;
            }

            stringBuilder.append( hex ).append( " " );
            total++;

            if ( ++count == 32 ) {
                StringBuilder intDisplay = new StringBuilder( Integer.toString( total ) );
                int missingTrailing = 8 - intDisplay.length();
                for ( int i = 0; i < missingTrailing; i++ ) {
                    intDisplay.insert( 0, "0" );
                }

                stringBuilder.append( " " ).append( "\n " ).append( intDisplay ).append( ": " );
                count = 0;
            }
        }

        LOGGER.info( stringBuilder.toString() );
    }

    public static void dumpNBTCompund( NBTTagCompound compound ) {
        LOGGER.info( "COMPOUND START" );
        dumpNBTTag( compound, 0 );
        LOGGER.info( "COMPOUND END" );
    }

    private static void dumpNBTTag( NBTTagCompound entity, int depth ) {
        for ( Map.Entry<String, Object> stringObjectEntry : entity.entrySet() ) {
            Object obj = stringObjectEntry.getValue();
            if ( obj instanceof List ) {
                LOGGER.info( Strings.repeat( " ", depth * 2 ) + stringObjectEntry.getKey() + ": [" );

                List v = (List) obj;
                if ( v.size() > 0 ) {
                    LOGGER.info( Strings.repeat( " ", ( depth + 1 ) * 2 ) + "-----------" );
                }

                for ( Object o : v ) {
                    if ( o instanceof NBTTagCompound ) {
                        dumpNBTTag( (NBTTagCompound) o, depth + 1 );
                        LOGGER.info( Strings.repeat( " ", ( depth + 1 ) * 2 ) + "-----------" );
                    } else {
                        LOGGER.info( Strings.repeat( " ", ( depth + 1 ) * 2 ) + o );
                    }
                }

                if ( v.size() > 0 ) {
                    LOGGER.info( Strings.repeat( " ", ( depth + 1 ) * 2 ) + "-----------" );
                }

                LOGGER.info( Strings.repeat( " ", depth * 2 ) + "]" );
            } else if ( obj instanceof NBTTagCompound ) {
                LOGGER.info( Strings.repeat( " ", depth * 2 ) + stringObjectEntry.getKey() + ": " );
                dumpNBTTag( (NBTTagCompound) obj, depth + 1 );
            } else {
                LOGGER.info( Strings.repeat( " ", depth * 2 ) + stringObjectEntry.getKey() + ": " + obj + "(" + obj.getClass() + ")" );
            }
        }
    }
}
