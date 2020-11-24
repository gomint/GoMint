/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Supplier;

public class LambdaConstructionFactory<T> implements ConstructionFactory<T> {

    private final Supplier<T> function;

    public LambdaConstructionFactory(Class<? extends T> clazz) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        CallSite site;

        try {
            MethodHandle mh = lookup.findConstructor(clazz, MethodType.methodType(void.class));

            site = LambdaMetafactory.metafactory(lookup,
                "get",
                MethodType.methodType(Supplier.class),
                mh.type().generic(),
                mh,
                mh.type());
        } catch (LambdaConversionException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException("Lambda creation failed for constructor", e);
        }

        try {
            this.function = (Supplier<T>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new IllegalArgumentException("Lambda creation failed for constructor", e);
        }
    }

    @Override
    public T newInstance() {
        return this.function.get();
    }

}
