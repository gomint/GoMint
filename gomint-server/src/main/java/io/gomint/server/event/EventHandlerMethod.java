/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.event;

import io.gomint.event.Event;
import io.gomint.event.EventHandler;
import io.gomint.event.EventListener;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.plugin.PluginClassloader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author BlackyPaw
 * @version 1.0
 */
class EventHandlerMethod implements Comparable<EventHandlerMethod> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandlerMethod.class);

    private static final AtomicInteger PROXY_COUNT = new AtomicInteger(0);

    private final EventHandler annotation;
    private EventProxy proxy;

    // For toString reference
    private final EventListener instance;

    /**
     * Construct a new data holder for a EventHandler.
     *
     * @param instance   The instance of the EventHandler which should be used to invoke the EventHandler Method
     * @param method     The method which should be invoked when the event arrives
     * @param annotation The annotation which holds additional information about this EventHandler Method
     */
    EventHandlerMethod(final EventListener instance, final Method method, final EventHandler annotation) {
        this.annotation = annotation;
        this.instance = instance;

        // Build up proxy
        try {
            if (instance.getClass().getClassLoader() instanceof PluginClassloader) {
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

                String listenerClassName = instance.getClass().getName().replace(".", "/");
                String className = listenerClassName + "Proxy" + PROXY_COUNT.incrementAndGet();
                String eventClassName = method.getParameterTypes()[0].getName().replace(".", "/");

                // Define the class
                cw.visit(Opcodes.V11,
                    Opcodes.ACC_PUBLIC,
                    className,
                    null,
                    "java/lang/Object",
                    new String[]{"io/gomint/server/event/EventProxy"});

                // Define the obj field
                cw.newField(className, "obj", "L" + listenerClassName + ";");
                cw.visitField(Opcodes.ACC_PUBLIC, "obj", "L" + listenerClassName + ";", null, null);

                // Build constructor
                MethodVisitor con = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
                con.visitCode();
                con.visitVarInsn(Opcodes.ALOAD, 0);
                con.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                con.visitInsn(Opcodes.RETURN);
                con.visitMaxs(1, 1);

                // Build call method
                MethodVisitor callCon = cw.visitMethod(Opcodes.ACC_PUBLIC, "call", "(Lio/gomint/event/Event;)V", null, null);
                callCon.visitCode();
                callCon.visitVarInsn(Opcodes.ALOAD, 0);
                callCon.visitFieldInsn(Opcodes.GETFIELD, className, "obj", "L" + listenerClassName + ";");
                callCon.visitVarInsn(Opcodes.ALOAD, 1);
                callCon.visitTypeInsn(Opcodes.CHECKCAST, eventClassName);
                callCon.visitMethodInsn(Opcodes.INVOKEVIRTUAL, listenerClassName, method.getName(), "(L" + eventClassName + ";)V", false);
                callCon.visitInsn(Opcodes.RETURN);
                callCon.visitMaxs(2, 2);

                // Get bytecode
                byte[] data = cw.toByteArray();

                MethodHandles.Lookup lookup = MethodHandles.lookup();
                MethodHandles.Lookup privateLookup = MethodHandles.privateLookupIn(instance.getClass(), lookup);
                Class<? extends EventProxy> proxyClass = (Class<? extends EventProxy>) privateLookup.defineClass(data);

                this.proxy = proxyClass.getDeclaredConstructor().newInstance();
                this.proxy.getClass().getDeclaredField("obj").set(this.proxy, instance);
            } else {
                throw new IllegalArgumentException("Only plugins are allowed to register event listeners");
            }
        } catch (Exception e) {
            LOGGER.error("Could not construct new proxy for " + method.toString(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventHandlerMethod that = (EventHandlerMethod) o;
        return Objects.equals(annotation, that.annotation) &&
            Objects.equals(proxy, that.proxy) &&
            Objects.equals(instance, that.instance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, proxy, instance);
    }

    @Override
    public String toString() {
        return "EventHandlerMethod{" +
            "instance=" + instance +
            '}';
    }

    /**
     * Invoke this Event handler.
     *
     * @param event Event which should be handled in this handler
     */
    public void invoke(Event event) {
        try {
            this.proxy.call(event);
        } catch (Throwable cause) {
            LOGGER.warn("Event handler has thrown a exception: ", cause);
            ReportUploader.create().exception(cause).upload();
        }
    }

    /**
     * Returns true when this EventHandler accepts cancelled events
     *
     * @return true when it wants to accept events when cancelled, false if not
     */
    boolean ignoreCancelled() {
        return this.annotation.ignoreCancelled();
    }

    @Override
    public int compareTo(EventHandlerMethod o) {
        return (Byte.compare(this.annotation.priority().value(), o.annotation.priority().value()));
    }

}
