/*
 * Copyright (c) 2018 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

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
