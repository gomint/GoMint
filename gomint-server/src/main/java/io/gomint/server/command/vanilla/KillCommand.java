package io.gomint.server.command.vanilla;

import io.gomint.GoMint;
import io.gomint.command.Command;
import io.gomint.command.CommandOutput;
import io.gomint.command.CommandSender;
import io.gomint.command.PlayerCommandSender;
import io.gomint.command.annotation.Description;
import io.gomint.command.annotation.Name;
import io.gomint.command.annotation.Overload;
import io.gomint.command.annotation.Parameter;
import io.gomint.command.validator.TargetValidator;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.world.Gamemode;

import java.util.Map;

/**
 * @author Kaooot
 * @version 1.0
 */
@Name( "kill" )
@Description( "Kills entities (players, mobs, etc.)." )
@Overload( {
    @Parameter( name = "target", validator = TargetValidator.class )
} )
public class KillCommand extends Command {

    @Override
    public CommandOutput execute( CommandSender commandSender, String alias, Map<String, Object> arguments ) {
        CommandOutput commandOutput = new CommandOutput();

        EntityPlayer target = (EntityPlayer) arguments.get( "target" );

        if ( target != null ) {
            target.setHealth( 0 );
            GoMint.instance().getPlayers().forEach( players -> players.sendMessage( target.getName() + " died" ) );
            commandOutput.success( "Killed " + target.getName() );
        } else {
            if ( commandSender instanceof PlayerCommandSender ) {
                EntityPlayer player = (EntityPlayer) commandSender;

                if ( player.getGamemode() != Gamemode.CREATIVE ) {
                    player.setHealth( 0 );
                    GoMint.instance().getPlayers().forEach( players -> players.sendMessage( player.getName() + " died" ) );
                    commandOutput.success( "Killed " + player.getName() );
                }
            }
        }
        return commandOutput;
    }
}
