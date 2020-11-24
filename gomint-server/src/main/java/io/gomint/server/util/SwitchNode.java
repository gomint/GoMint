/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import org.apache.commons.text.WordUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SwitchNode {

    private final Map<String, SwitchNode> nodes = new HashMap<>();

    private String key;
    private String type;

    private String value;

    public SwitchNode getOrGenerate(Object key) {
        String rep;
        if (key instanceof String) {
            rep = "\"" + key + "\"";
        } else {
            rep = String.valueOf(key);
        }

        return this.nodes.computeIfAbsent(rep, k -> new SwitchNode());
    }

    public void setValue(Object value) {
        if (value instanceof String) {
            this.value = "\"" + value + "\"";
        } else {
            this.value = String.valueOf(value);
        }
    }

    public void setKey(Object key) {
        this.key = String.valueOf(key);
    }

    public void setType(Class<?> t) {
        this.type = t.getName();
    }

    public String print(int indents, String parent, Set<String> methods) {
        if (this.value != null) {
            return "return " + this.value + ";\n";
        }

        StringBuilder builder = new StringBuilder();
        if (parent != null) {
            builder.append(repeatSpace(indents)).append("private int ").append(parent).append("(Map<String, Object> input) {\n");
        }

        builder.append(repeatSpace(indents + 4)).append(this.type).append(" ").append(this.key).append(" = (").append(this.type).append(") input.get(\"").append(this.key).append("\");").append("\n");
        builder.append(repeatSpace(indents + 4)).append("switch ( ").append(this.key).append(" ) { ").append("\n");
        for (Map.Entry<String, SwitchNode> entry : this.nodes.entrySet()) {
            String methodName = makeNice(((parent != null) ? parent : "") + this.key + entry.getKey());

            builder.append(repeatSpace(indents + 8)).append("case ").append(entry.getKey()).append(": ").append("\n");

            if (entry.getValue().value == null) {
                builder.append(repeatSpace(indents + 12)).append("return this.").append(methodName).append("(input);").append("\n");
                methods.add(entry.getValue().print(indents, methodName, methods));
            } else {
                builder.append(repeatSpace(indents + 12)).append(entry.getValue().print(indents, null, methods));
            }
        }

        builder.append(repeatSpace(indents + 4)).append("}").append("\n\n");
        builder.append(repeatSpace(indents + 4)).append("return -1;\n");

        if (parent != null) {
            builder.append(repeatSpace(indents)).append("}\n");
        }

        return builder.toString();
    }

    private String makeNice(String methodName) {
        methodName = methodName.replaceAll("Resolver", "");
        methodName = WordUtils.capitalize(methodName, '_').replaceAll("_", "");
        methodName = methodName.replaceAll("\"", "");
        return methodName + "Resolver";
    }

    private String repeatSpace(int indents) {
        return " ".repeat(Math.max(0, indents));
    }

}
