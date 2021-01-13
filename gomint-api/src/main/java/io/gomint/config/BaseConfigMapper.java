/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class BaseConfigMapper extends BaseConfig {

    private static final transient Charset CHARSET;
    private static final transient Yaml YAML;
    private static final transient Representer REPRESENTER;

    static {
        CHARSET = Charset.forName("UTF-8");

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(2);

        REPRESENTER = new Representer();
        REPRESENTER.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        ClassLoader classLoader = BaseConfigMapper.class.getClassLoader();
        YAML = new Yaml(new CustomClassLoaderConstructor(classLoader), REPRESENTER, options);
    }

    protected transient ConfigSection root;
    private transient Map<String, ArrayList<String>> comments;
    private transient String commentPrefix;

    protected BaseConfigMapper() {
        this.comments = new LinkedHashMap<>();
        this.commentPrefix = "";
        // Configure the settings for serializing via the annotations preset
        this.configureFromSerializeOptionsAnnotation();
    }

    public BaseConfigMapper addComment(String key, String value) {
        if (!this.comments.containsKey(key)) {
            this.comments.put(key, new ArrayList<>());
        }

        for (String split : value.split("\n")) {
            this.comments.get(key).add(split);
        }
        return this;
    }

    public BaseConfigMapper clearComments() {
        this.comments.clear();
        return this;
    }

    public BaseConfigMapper mergeComments(Map<String, String> comments) {
        for (Map.Entry<String, String> entry : comments.entrySet()) {
            String commentPath = this.commentPrefix + "." + entry.getKey();

            if (!this.comments.containsKey(commentPath)) {
                this.addComment(commentPath, entry.getValue());
            }
        }
        return this;
    }

    public BaseConfigMapper resetCommentPrefix(String path) {
        this.commentPrefix = path;
        return this;
    }

    public BaseConfigMapper addCommentPrefix(String path) {
        this.commentPrefix += "." + path;
        return this;
    }

    public BaseConfigMapper removeCommentPrefix(String path) {
        if (this.commentPrefix.endsWith(path)) {
            this.commentPrefix = this.commentPrefix.substring(0, this.commentPrefix.length() - (1 + path.length()));
        }
        return this;
    }

    protected BaseConfigMapper loadFromYaml() throws InvalidConfigurationException {
        this.root = new ConfigSection();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(this.configFile), CHARSET)) {
            Object object = YAML.load(reader);

            if (object != null) {
                convertMapsToSections((Map<?, ?>) object, this.root);
            }
        } catch (IOException | ClassCastException | YAMLException cause) {
            throw new InvalidConfigurationException("Failed loading from YAML file " +
                "\"" + this.configFile.getAbsolutePath() + "\"", cause);
        }
        return this;
    }

    protected BaseConfigMapper saveToYaml() throws InvalidConfigurationException {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.configFile), CHARSET)) {
            this.writeHeader(writer);

            int depth = 0;
            List<String> keyChain = new ArrayList<>();

            String yamlString = YAML.dump(this.root.getValues(true));
            StringBuilder writeLines = new StringBuilder();

            for (String line : yamlString.split("\n")) {
                if (line.startsWith(new String(new char[depth]).replace("\0", " "))) {
                    keyChain.add(line.split(":")[0].trim());
                    depth = depth + 2;
                } else {
                    if (line.startsWith(new String(new char[depth - 2]).replace("\0", " "))) {
                        if (!line.startsWith(new String(new char[depth - 2]).replace("\0", " ") + "-")) {
                            keyChain.remove(keyChain.size() - 1);
                        } else {
                            keyChain.add("-");
                        }
                    } else {
                        // Check how much spaces are in front of the line
                        int spaces = this.calcSpaces(line);
                        depth = spaces;

                        if (spaces == 0) {
                            keyChain = new ArrayList<>();
                            depth = 2;
                        } else {
                            List<String> temp = new ArrayList<>();
                            int index = 0;
                            for (int i = 0; i < spaces; i = i + 2, index++) {
                                temp.add(keyChain.get(index));
                            }

                            keyChain = temp;
                            depth = depth + 2;
                        }
                    }

                    if (!keyChain.isEmpty() && keyChain.get(keyChain.size() - 1).equals("-") && line.trim().startsWith("-")) {
                        keyChain.add(line.split(":")[0].trim().substring(1).trim());
                    } else {
                        keyChain.add(line.split(":")[0].trim());
                    }
                }

                String search;
                if (!keyChain.isEmpty()) {
                    search = String.join(".", keyChain);
                } else {
                    search = "";
                }

                int useDepth = depth - 2;
                if (line.trim().startsWith("-")) {
                    keyChain.remove(keyChain.size() - 1);
                    useDepth += 2;
                }

                if (this.comments.containsKey(search)) {
                    for (String comment : this.comments.get(search)) {
                        writeLines.append(new String(new char[useDepth]).replace("\0", " "));
                        writeLines.append("# ");
                        writeLines.append(comment);
                        writeLines.append("\n");
                    }
                }

                writeLines.append(line);
                writeLines.append("\n");
            }

            writer.write(writeLines.toString());
        } catch (IOException cause) {
            throw new InvalidConfigurationException("Failed saving to YAML file " +
                "\"" + this.configFile.getAbsolutePath() + "\"", cause);
        }

        return this;
    }

    private BaseConfigMapper writeHeader(OutputStreamWriter writer) throws IOException {
        if (this.configHeader != null) {
            for (String line : this.configHeader) {
                writer.write("# " + line + "\n");
            }

            writer.write("\n");
        }
        return this;
    }

    private int calcSpaces(String line) {
        int spaces = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') {
                spaces++;
            } else {
                break;
            }
        }

        return spaces;
    }

    private BaseConfigMapper convertMapsToSections(Map<?, ?> input, ConfigSection section) {
        if (input == null) {
            return this;
        }

        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                this.convertMapsToSections((Map<?, ?>) value, section.create(key));
            } else {
                section.set(key, value, false);
            }
        }

        return this;
    }

}
