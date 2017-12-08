/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.command;

import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandOverload;
import io.gomint.command.ParamValidator;
import io.gomint.command.validator.CommandValidator;
import io.gomint.entity.EntityPlayer;
import io.gomint.plugin.Plugin;
import io.gomint.server.entity.CommandPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SubCommand extends Command {

    private final Plugin plugin;
    private final Map<String, CommandHolder> subCommands = new HashMap<>();

    /**
     * Create a new sub command container for the plugin
     *
     * @param plugin   which created this sub command
     * @param baseName of the command
     */
    SubCommand( Plugin plugin, String baseName ) {
        super( baseName );
        this.plugin = plugin;
    }

    public void addCommand( Plugin plugin, String subCommandName, CommandHolder holder ) {
        if ( plugin == null || this.plugin.equals( plugin ) ) {
            this.subCommands.put( subCommandName, holder );
        }
    }

    @Override
    public CommandOutput execute( EntityPlayer player, String alias, Map<String, Object> arguments ) {
        // Look out for subCmd# keys
        for ( Map.Entry<String, Object> entry : arguments.entrySet() ) {
            if ( entry.getKey().equals( entry.getValue() ) && this.subCommands.containsKey( entry.getKey() ) ) {
                String subCommand = (String) entry.getValue();
                CommandHolder commandHolder = this.subCommands.get( subCommand );
                if ( commandHolder != null ) {
                    if ( commandHolder.getPermission() == null || player.hasPermission( commandHolder.getPermission() ) ) {
                        Map<String, Object> arg = new HashMap<>( arguments );
                        arg.remove( entry.getKey() );

                        return commandHolder.getExecutor().execute( player, alias + " " + entry.getValue(), arg );
                    } else {
                        CommandOutput commandOutput = new CommandOutput();
                        commandOutput.fail( "No permission for this command" );
                        return commandOutput;
                    }
                }
            }
        }

        CommandOutput commandOutput = new CommandOutput();
        commandOutput.fail( "Command for input '%%s' could not be found", this.getName() );
        return commandOutput;
    }

    /**
     * Create the correct command holder for package assembly
     *
     * @param player for which we need this sub command
     * @return null when no sub commands apply, a holder when they apply
     */
    CommandHolder createHolder( io.gomint.server.entity.EntityPlayer player ) {
        List<CommandOverload> overloads = new ArrayList<>();

        for ( Map.Entry<String, CommandHolder> entry : this.subCommands.entrySet() ) {
            if ( entry.getValue().getPermission() == null || player.hasPermission( entry.getValue().getPermission() ) ) {
                if ( entry.getValue().getOverload() != null ) {
                    for ( CommandOverload commandOverload : entry.getValue().getOverload() ) {
                        CommandOverload overload = new CommandOverload();
                        CommandValidator commandValidator = new CommandValidator();
                        overload.param( entry.getKey(), commandValidator );

                        for ( Map.Entry<String, ParamValidator> validatorEntry : commandOverload.getParameters().entrySet() ) {
                            overload.param( validatorEntry.getKey(), validatorEntry.getValue() );
                        }

                        overloads.add( overload );
                    }
                } else {
                    CommandOverload overload = new CommandOverload();
                    CommandValidator commandValidator = new CommandValidator();
                    overload.param( entry.getKey(), commandValidator );
                    overloads.add( overload );
                }
            }
        }

        if ( !overloads.isEmpty() ) {
            return new CommandHolder( this.getName(),
                "Subcommands for command '" + this.getName() + "'",
                null,
                CommandPermission.NORMAL,
                null,
                this,
                overloads );
        }

        return null;
    }

}
