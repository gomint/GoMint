/*
 * Copyright (c) 2015, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Digot
 * @version 1.0
 */
@AllArgsConstructor
public abstract class Command {
    @Getter private String name;
    @Getter private String[] aliases;
    @Getter private String permission;
    @Getter private String description;

    public Command ( String name ) {
        this.name = name;
    }

    public Command ( String name, String description ) {
        this.name = name;
        this.description = description;
    }

    public abstract void execute( CommandSender sender, String[] args );

    @Override
    public String toString ( ) {
        return "/" + name + ( description != null ? " - " + description : "");
    }
}
