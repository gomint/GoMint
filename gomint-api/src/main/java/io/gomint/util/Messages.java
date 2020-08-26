package io.gomint.util;

import com.google.common.base.Preconditions;

/**
 * @author Clockw1seLrd
 * @version 1.0
 * @stability 3
 */
public class Messages {

    private static final String PARAM_IS_NULL_FORMAT = "Passed parameter '%s' cannot be null";

    public static String paramIsNull( String paramName ) {
        Preconditions.checkNotNull( paramName, String.format( PARAM_IS_NULL_FORMAT, "paramName" ) );

        return String.format( PARAM_IS_NULL_FORMAT, paramName );
    }

}
