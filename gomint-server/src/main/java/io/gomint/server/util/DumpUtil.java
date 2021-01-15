/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import com.google.common.base.Strings;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.item.ItemStack;
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

    public static void dumpInventory( Inventory<?> inventory ) {
        LOGGER.info( "Inventory: {} - Size: {}", inventory.getClass().getName(), inventory.size() );

        if ( inventory.size() == 1 ) {
            StringBuilder builder = new StringBuilder( "  " );
            ItemStack<?> item = (ItemStack<?>) inventory.item( 0 );
            String slotName = String.valueOf( 0 );
            String itemName = String.valueOf( item.material() );
            String dataName = String.valueOf( item.data() );
            String amountName = String.valueOf( item.amount() );
            int itemDataNameLength = itemName.length() + dataName.length() + amountName.length();

            builder
                .append( slotName )
                .append( "  " )
                .append( itemName )
                .append( ":" )
                .append( dataName )
                .append( "x" )
                .append( amountName )
                .append( Strings.repeat( " ", 7 - itemDataNameLength ) );

            LOGGER.info( builder.toString() );
        } else if ( inventory.size() > 9 ) {
            int rowSize = inventory.size() / 9;
            for ( int i = 0; i < rowSize; i++ ) {
                StringBuilder builder = new StringBuilder( "  " );

                for ( int j = 0; j < 9; j++ ) {
                    ItemStack<?> item = (ItemStack<?>) inventory.item( i * 9 + j );
                    String slotName = String.valueOf( i * 9 + j );
                    String itemName = String.valueOf( item.material() );
                    String dataName = String.valueOf( item.data() );
                    String amountName = String.valueOf( item.amount() );
                    int itemDataNameLength = itemName.length() + dataName.length() + amountName.length();

                    builder
                        .append( slotName )
                        .append( slotName.length() == 2 ? " " : "  " )
                        .append( itemName )
                        .append( ":" )
                        .append( dataName )
                        .append( "x" )
                        .append( amountName )
                        .append( Strings.repeat( " ", 7 - itemDataNameLength ) );
                }

                LOGGER.info( builder.toString() );
            }
        } else {
            int rowSize = inventory.size() == 4 ? 2 : 3;
            for ( int i = 0; i < rowSize; i++ ) {
                StringBuilder builder = new StringBuilder( "  " );

                for ( int j = 0; j < rowSize; j++ ) {
                    ItemStack<?> item = (ItemStack<?>) inventory.item( i * rowSize + j );
                    String slotName = String.valueOf( i * rowSize + j );
                    String itemName = String.valueOf( item.material() );
                    String dataName = String.valueOf( item.data() );
                    String amountName = String.valueOf( item.amount() );
                    int itemDataNameLength = itemName.length() + dataName.length() + amountName.length();

                    builder
                        .append( slotName )
                        .append( slotName.length() == 2 ? " " : "  " )
                        .append( itemName )
                        .append( ":" )
                        .append( dataName )
                        .append( "x" )
                        .append( amountName )
                        .append( Strings.repeat( " ", 7 - itemDataNameLength ) );
                }

                LOGGER.info( builder.toString() );
            }
        }
    }

    public static void dumpPacketbuffer( PacketBuffer buffer ) {
        LOGGER.info("-------------------------------------------------------------------");

        int pos = buffer.getReadPosition();

        StringBuilder lineBuilder = new StringBuilder();
        // StringBuilder stringRepBuilder = new StringBuilder();
        while ( buffer.getRemaining() > 0 ) {
            for ( int i = 0; i < 64 && buffer.getRemaining() > 0; ++i ) {
                byte b = buffer.readByte();
                String hex = Integer.toHexString( ( (int) b ) & 0xFF );
                if ( hex.length() < 2 ) {
                    hex = "0" + hex;
                }

                // stringRepBuilder.append( (char) ( b & 0xFF ) );
                lineBuilder.append( hex );
                if ( i + 1 < 64 && buffer.getRemaining() > 0 ) {
                    lineBuilder.append( " " );
                }
            }

            // lineBuilder.append( " " ).append( stringRepBuilder );

            LOGGER.info( lineBuilder.toString() );
            lineBuilder = new StringBuilder();
            // stringRepBuilder = new StringBuilder();
        }

        buffer.setReadPosition(pos);
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

                List<?> v = (List<?>) obj;
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
