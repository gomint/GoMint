package io.gomint.server.command.internal;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandExecutor;
import io.gomint.command.CommandOutput;
import io.gomint.command.validator.StringValidator;
import io.gomint.entity.Player;
import io.gomint.server.command.CommandManager;

import java.util.Collection;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ListCommand extends CommandExecutor {

    /**
     * Command to list all online players
     *
     * @param commandManager which should register this command
     */
    public ListCommand( CommandManager commandManager ) {
        Command command = new Command( "list" );
        command.description( "List online players" ).executor( this ).permission( "gomint.commands.list" );
        command.overload();                                                             // Default /list without any parameters
        command.overload().param( "filter", new StringValidator( "[A-Za-z0-9_]+" ) );   // Name filter

        commandManager.register( null, command );
    }

    @Override
    public CommandOutput execute( Player player, Map<String, Object> arguments ) {
        CommandOutput output = new CommandOutput();

        Collection<Player> players = GoMint.instance().getPlayers();
        output.success( "§7[§aSYSTEM§7] §fCurrently online: §e%%s", players.size() );

        if ( arguments.containsKey( "filter" ) ) {
            // There is a filter present
            String filter = (String) arguments.get( "filter" );
            int i = 0;
            for ( Player player1 : players ) {
                if ( player1.getName().startsWith( filter ) ) {
                    if ( i++ > 20 ) {
                        output.success( "§7[§aSYSTEM§7] §f ... and more. Please use §e'/list <filter: string>'" );
                        break;
                    }

                    output.success( "§7[§aSYSTEM§7] §f%%s", player1.getName() );
                }
            }
        } else {
            int i = 0;
            for ( Player player1 : players ) {
                if ( i++ > 20 ) {
                    output.success( "§7[§aSYSTEM§7] §f ... and more. Please use §e'/list <filter: string>'" );
                    break;
                }

                output.success( "§7[§aSYSTEM§7] §f%%s", player1.getName() );
            }
        }

        return output;
    }

}
