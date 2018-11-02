package io.gomint.command.annotation;

import io.gomint.command.ParamValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author geNAZt
 * @version 1.0
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface Parameter {

    String name();

    Class<? extends ParamValidator> validator();

    String[] arguments() default {};

    boolean optional() default false;

    String postfix() default "";

}
