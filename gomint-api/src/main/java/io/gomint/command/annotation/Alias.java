package io.gomint.command.annotation;

import java.lang.annotation.*;

/**
 * @author geNAZt
 * @version 1.0
 */
@Target( ElementType.TYPE )
@Repeatable( Aliases.class )
@Retention( RetentionPolicy.RUNTIME )
public @interface Alias {

    String value();

}
