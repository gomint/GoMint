/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.annotation;

import io.gomint.config.ConfigMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface SerializeOptions {

    String[] configHeader() default {};

    boolean skipFailedObjects() default false;

    ConfigMode configMode() default ConfigMode.FIELD_IS_KEY;

}
