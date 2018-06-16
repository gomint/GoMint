package io.gomint.command.annotation;

import java.lang.annotation.*;

/**
 * @author geNAZt
 * @version 1.0
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface Overloads {

    Overload[] value();

}
