/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface Scope {

    /**
     * @see io.gomint.command.Command#activeWorldsOnly(boolean)
     */
    boolean activeWorldsOnly() default true;

    /**
     * @see io.gomint.command.Command#console(boolean)
     */
    boolean console() default true;
}
