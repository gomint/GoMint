package io.gomint.server.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author geNAZt
 * @version 1.0
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface RegisterInfo {

    int id();

}
