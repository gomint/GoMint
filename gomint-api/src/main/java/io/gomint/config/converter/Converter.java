/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import java.lang.reflect.ParameterizedType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface Converter {

    /**
     * This method gets called on save. It gets the Fields Type and the object the Config wants to save into it. This
     * is needed to pretty print INTO the config.
     *
     * @param type The type (Class) of the Field
     * @param object The object which is stored in the Config Object
     * @param parameterizedType If the Class has some generic information this is the information otherwise this is null
     * @return An Object (mostly a Map or a List)
     * @throws Exception Some generic exception when something went wrong. This gets caught by the converter
     */
    Object toConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception;

    /**
     * This method gets called when we want to load something out of the File. You get that what you give into the Config
     * via toConfig as Object passed. The type is the Destination Field Type which this Object should be layed in.
     *
     * @param type The type (Class) of the Field
     * @param object The Object from toConfig
     * @param parameterizedType If the Class has some generic information this is the information otherwise this is null
     * @return The correct Object which can be hold by the Field
     * @throws Exception Some generic exception when something went wrong. This gets caught by the converter
     */
    Object fromConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception;

    /**
     * This checks if this converter can convert the given Class
     *
     * @param type The type (Class) of the Field to check
     * @return true if this can convert that otherwise false
     */
    boolean supports( Class<?> type );

}
