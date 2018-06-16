package io.gomint.command.annotation;

import java.lang.annotation.*;

/**
 * @author geNAZt
 * @version 1.0
 */
@Target( ElementType.TYPE )
@Repeatable( Overloads.class )
@Retention( RetentionPolicy.RUNTIME )
public @interface Overload {

    Parameter[] value() default {};

}
