package io.gomint.config;

import java.lang.annotation.*;

/**
 * @author geNAZt
 * @version 1.0
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
@Repeatable( Comments.class )
public @interface Comment {
    String value() default "";
}
