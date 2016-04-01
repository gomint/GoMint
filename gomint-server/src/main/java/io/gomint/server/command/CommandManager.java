package io.gomint.server.command;

import io.gomint.command.Command;
import io.gomint.command.CommandSender;
import io.gomint.plugin.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Digot
 * @version 1.0
 */
public class CommandManager {

    private final Set<Command> registeredCommands;
    private final Logger logger;

    public CommandManager (){
        this.registeredCommands = new HashSet<>();
        this.logger = LoggerFactory.getLogger( CommandManager.class );
    }

    public void registerCommand( Command command ) {
        synchronized ( this.registeredCommands ) {
            this.registeredCommands.add( command );
        }
    }

    public Command getCommand( String nameOrAlias ) {
        synchronized ( this.registeredCommands ) {
            for ( Command command : this.registeredCommands ) {
                if( command.getName().equalsIgnoreCase( nameOrAlias ) ) {
                    return command;
                }
                else {
                    //Check for aliases
                    for ( String s : command.getAliases() ) {
                        if( nameOrAlias.equalsIgnoreCase( s ) ) {
                            return command;
                        }
                    }
                }
            }
        }

        return null;
    }

    public void executeCommand( CommandSender sender, String name, String[] args ) {
        Command command = getCommand( name );

        if(command == null) {
            this.logger.info( sender.getName() + " issued unknown server command '" + name + "'" );
            return;
        }

        //Args may be null but we don't have to care about it
        command.execute( sender, args );
    }


}
