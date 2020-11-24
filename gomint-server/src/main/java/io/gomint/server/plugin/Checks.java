/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin;

import java.util.Set;

public class Checks {

    // keywords, boolean and null literals, not allowed in identifiers
    private static final Set<String> RESERVED = Set.of(
        "abstract",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "default",
        "do",
        "double",
        "else",
        "enum",
        "extends",
        "final",
        "finally",
        "float",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "new",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "switch",
        "synchronized",
        "this",
        "throw",
        "throws",
        "transient",
        "try",
        "void",
        "volatile",
        "while",
        "true",
        "false",
        "null",
        "_"
    );

    /**
     * Returns {@code true} if the given name is a legal class name.
     */
    public static boolean isClassName(String name) {
        return isTypeName(name);
    }

    /**
     * Returns {@code true} if the given name is a legal package name.
     */
    public static boolean isPackageName(String name) {
        return isTypeName(name);
    }

    /**
     * Returns {@code true} if the given name is a legal type name.
     */
    private static boolean isTypeName(String name) {
        int next;
        int off = 0;
        while ((next = name.indexOf('.', off)) != -1) {
            String id = name.substring(off, next);
            if (!isJavaIdentifier(id))
                return false;
            off = next+1;
        }
        String last = name.substring(off);
        return isJavaIdentifier(last);
    }

    /**
     * Returns true if the given char sequence is a legal Java identifier,
     * otherwise false.
     */
    private static boolean isJavaIdentifier(CharSequence cs) {
        if (cs.length() == 0 || RESERVED.contains(cs))
            return false;

        int first = Character.codePointAt(cs, 0);
        if (!Character.isJavaIdentifierStart(first))
            return false;

        int i = Character.charCount(first);
        while (i < cs.length()) {
            int cp = Character.codePointAt(cs, i);
            if (!Character.isJavaIdentifierPart(cp))
                return false;
            i += Character.charCount(cp);
        }

        return true;
    }

}
