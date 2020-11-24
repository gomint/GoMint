/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;

public class ASMFactoryConstructionFactory {

    public static <T> ConstructionFactory<T> create(Class<? extends T> clazz) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandles.Lookup privateLookup = null;

        try {
            privateLookup = MethodHandles.privateLookupIn(ASMFactoryConstructionFactory.class, lookup);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        String factoryClassName = "io/gomint/server/util/performance/" + clazz.getSimpleName() + "Factory";
        String dottedFactoryClassName = factoryClassName.replace("/", ".");

        try {
            return (ConstructionFactory<T>) privateLookup.findClass(dottedFactoryClassName).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            // Ignore, we create a new factory now
        }

        String clazzName = clazz.getName().replace(".", "/");

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // Define the class
        cw.visit(Opcodes.V11,
            Opcodes.ACC_PUBLIC,
            "io/gomint/server/util/performance/" + clazz.getSimpleName() + "Factory",
            null,
            "java/lang/Object",
            new String[]{"io/gomint/server/util/performance/ConstructionFactory"});

        // Build constructor
        MethodVisitor con = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        con.visitCode();
        con.visitVarInsn(Opcodes.ALOAD, 0);
        con.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        con.visitInsn(Opcodes.RETURN);
        con.visitMaxs(1, 1);

        // Build newInstance method
        MethodVisitor callCon = cw.visitMethod(Opcodes.ACC_PUBLIC, "newInstance", "()Ljava/lang/Object;", null, null);
        callCon.visitCode();
        callCon.visitTypeInsn(Opcodes.NEW, clazzName);
        callCon.visitInsn(Opcodes.DUP);
        callCon.visitMethodInsn(Opcodes.INVOKESPECIAL, clazzName, "<init>", "()V", false);
        callCon.visitInsn(Opcodes.ARETURN);
        callCon.visitMaxs(2, 1);

        // Get bytecode
        byte[] data = cw.toByteArray();

        try {
            Class<? extends ConstructionFactory> proxyClass = (Class<? extends ConstructionFactory>) privateLookup.defineClass(data);
            return proxyClass.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

}
